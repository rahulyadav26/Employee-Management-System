package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.service.interfaces.SalaryServiceI;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalaryServiceImpl implements SalaryServiceI {

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private KafkaTemplate<String, SalaryUpdate> kafkaTemplateSalary;

    public final String SALARY_UPDATE_TOPIC = "SalaryUpdate";

    @Override
    public Salary addSalary(Long companyID, String employeeID, Salary salary) {
        Company company = companyRepo.findById(companyID).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeID);
        if(company==null){
            throw new NotExistsException("No such company exists");
        }
        if(employee==null){
            throw new NotExistsException("No such employee exists");
        }
        if(salary==null || salary.getSalary()==null){
            throw new InsufficientInformationException("Insufficient data found in request body");
        }
        if (!salary.getEmployeeId().equals(employeeID) || !salary.getCompanyId().equals(companyID)) {
            throw new DataMismatchException("Either the company id or employee id doesn't matches with the request");
        }
        if(salaryRepo.getSalaryById(employeeID)!=null){
            throw new DuplicateDataException("Data already exists");
        }
        return salaryRepo.save(salary);
    }

    @Override
    public Salary getSalary(Long companyId, String employeeId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Salary salary = salaryRepo.getSalaryById(employeeId);
        if (salary == null || company==null) {
            throw new NotExistsException("Either the company or salary of employee is not present");
        }
        return salary;
    }

    @Override
    public List<Salary> getSalaryList() {
        List<Salary> salaryList = salaryRepo.findAll();
        return salaryList;
    }

    @Override
    public String updateSalary(Long companyId, SalaryUpdate salaryUpdate) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        if(salaryUpdate==null){
            throw new EmptyUpdateException("Salary Update info is not valid");
        }
        if (salaryUpdate.getType().equals("0")) {
            //whole company
            List<Salary> salaryList = salaryRepo.salaryListComp(companyId);
            if (salaryUpdate.getSubType().equals("0")) {
                //update by amount
                for (int i = 0; i < salaryList.size(); i++) {
                    salaryList.get(i).setSalary(salaryList.get(i).getSalary() + salaryUpdate.getValue());
                }
            } else if (salaryUpdate.getSubType().equals("1")) {
                //update by percentage
                for (int i = 0; i < salaryList.size(); i++) {
                    double val = (salaryUpdate.getValue() / (double) 100) * salaryList.get(i).getSalary();
                    salaryList.get(i).setSalary(salaryList.get(i).getSalary() + val);
                }
            }
            else{
                throw new InsufficientInformationException("Subtype not valid");
            }
            cachingInfo.updateSalary(salaryList, company.getId());
        } else if(salaryUpdate.getType().equals("1")){
            //dept of a company
            Department department = departmentRepo.getDeptByCompId(companyId, salaryUpdate.getDepartmentName().toUpperCase());
            if(department==null){
                throw new NotExistsException("No such department exists");
            }
            List<Salary> salaryList = salaryRepo.salaryListCompDept(companyId, department.getId());
            if (salaryUpdate.getSubType().equals("0")) {
                //update by amount
                for (int i = 0; i < salaryList.size(); i++) {
                    salaryList.get(i).setSalary(salaryList.get(i).getSalary() + salaryUpdate.getValue());
                }
            } else if(salaryUpdate.getSubType().equals("1")){
                //update by percentage
                for (int i = 0; i < salaryList.size(); i++) {
                    double val = (salaryUpdate.getValue() / (double) 100) * salaryList.get(i).getSalary();
                    salaryList.get(i).setSalary(salaryList.get(i).getSalary() + val);
                }
            }
            else{
                throw new InsufficientInformationException("Subtype not valid");
            }
            cachingInfo.updateSalary(salaryList, company.getId());
        }
        else{
                throw new InsufficientInformationException("Type not valid");
        }
        kafkaTemplateSalary.send(SALARY_UPDATE_TOPIC, salaryUpdate);
        return StringConstant.UPDATE_SUCCESSFUL;
    }


}
