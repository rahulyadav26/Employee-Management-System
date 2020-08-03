package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
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
import com.assignment.application.update.SalaryEmployeeUpdate;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SalaryServiceImpl implements SalaryServiceI {

    public final String SALARY_UPDATE_TOPIC = "SalaryUpdate";

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

    @Override
    public Salary addSalary(Long companyID, String employeeID, Salary salary) {
        Company company = companyRepo.findById(companyID).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeID);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_EMPLOYEE_EXISTS);
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyID)) {
            throw new NotExistsException(
                    "Either department for employee is not valid or employee doesn't belong to the specified company");
        }
        if (salary == null || salary.getSalary() == null) {
            throw new InsufficientInformationException("Insufficient data found in request body");
        }
        List<Salary> checkSalary = salaryRepo.getSalaryById(employeeID);
        if (!checkSalary.isEmpty()) {
            throw new DuplicateDataException("Employee salary exists you can update the salary using update url");
        }
        salary.setIsCurrent(1L);
        salary.setCreatedAt(new Date());
        salary.setCreatedBy("0");
        salary.setEmployeeId(employeeID);
        return salaryRepo.save(salary);
    }

    @Override
    public Page<Salary> getSalary(Long companyId, String employeeId, Pageable pageable) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeId);
        Page<Salary> salary = salaryRepo.getSalaryById(employeeId, pageable);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("Company not exists in database");
        }
        if (employee == null || employee.getIsActive() == 0 || salary.getTotalElements() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_EMPLOYEE_EXISTS);
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0) {
            throw new NotExistsException("Department not exists");
        }
        if (!department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException("Company Id not valid for this employee");
        }
        return salary;
    }

    @Override
    public Page<Salary> getSalaryList(Pageable pageable) {
        Page<Salary> salaryList = salaryRepo.findAll(pageable);
        return salaryList;
    }

    //part of deployable set 3
    @Override
    public String updateSalary(Long companyId, SalaryUpdate salaryUpdate) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        if (salaryUpdate == null || salaryUpdate.getType() == null || salaryUpdate.getType().isEmpty() ||
            salaryUpdate.getSubType() == null || salaryUpdate.getSubType().isEmpty() ||
            salaryUpdate.getValue() == null) {
            throw new EmptyUpdateException("Salary Update info is not valid");
        }
        if (salaryUpdate.getType().equals("0")) {
            //whole company
            List<Long> departments = departmentRepo.getDepartmentOfCompany(companyId);
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
            } else {
                throw new InsufficientInformationException("Subtype not valid");
            }
            cachingInfo.updateSalary(salaryList, company.getId());
        } else if (salaryUpdate.getType().equals("1")) {
            //dept of a company
            Department department =
                    null; //departmentRepo.getDeptByCompId(companyId, salaryUpdate.getDepartmentName().toUpperCase());
            if (department == null) {
                throw new NotExistsException("No such department exists");
            }
            List<Salary> salaryList = salaryRepo.salaryListCompDept(companyId, department.getId());
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
            } else {
                throw new InsufficientInformationException("Subtype not valid");
            }
            cachingInfo.updateSalary(salaryList, company.getId());
        } else {
            throw new InsufficientInformationException("Type not valid");
        }
        kafkaTemplateSalary.send(SALARY_UPDATE_TOPIC, salaryUpdate);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public String updateSalaryOfEmployee(Long companyId, String employeeId, SalaryEmployeeUpdate salaryEmployeeUpdate) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeId);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_EMPLOYEE_EXISTS);
        }
        if (salaryEmployeeUpdate == null || salaryEmployeeUpdate.getValue() == null) {
            throw new InsufficientInformationException("Insufficient data found in request body");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyId)) {
            throw new NotExistsException("Company id is not valid for the employee");
        }
        List<Salary> salary = salaryRepo.getSalaryById(employeeId);
        if (salary.isEmpty()) {
            throw new NotExistsException("No entry found in database");
        }
        Salary currentSalary = salaryRepo.getCurrentSalaryById(employeeId);
        if (currentSalary != null) {
            currentSalary.setIsCurrent(0L);
            currentSalary.setUpdatedAt(new Date());
            currentSalary.setUpdatedBy("0");
            salaryRepo.save(currentSalary);
        }
        Salary salaryToBeUpdated = new Salary();
        salaryToBeUpdated.setSalary(salaryEmployeeUpdate.getValue());
        salaryToBeUpdated.setUpdatedBy(null);
        salaryToBeUpdated.setUpdatedAt(null);
        salaryToBeUpdated.setCreatedBy("0");
        salaryToBeUpdated.setCreatedAt(new Date());
        salaryToBeUpdated.setEmployeeId(employeeId);
        salaryRepo.save(salaryToBeUpdated);
        return StringConstant.UPDATE_SUCCESSFUL;
    }
}
