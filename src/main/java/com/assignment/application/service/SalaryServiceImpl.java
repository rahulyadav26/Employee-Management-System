package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.SalaryServiceI;
import com.assignment.application.entity.Department;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.update.SalaryUpdate;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StringConstant stringConstant;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public Salary addSalary(Long companyID, String employeeID, Salary salary) {
        Company company = companyRepo.findById(companyID).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeID);
        if (salary == null || company==null || employee==null || !salary.getEmployeeId().equals(employeeID) || !salary.getCompanyId().equals(companyID)) {
            return null;
        }
        return salaryRepo.save(salary);
    }

    @Override
    public Salary getSalary(Long companyId, String employeeId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Salary salary = salaryRepo.getSalaryById(employeeId);
        if (salary == null || company==null) {
            return null;
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
        if (company == null || salaryUpdate == null) {
            return stringConstant.invalidStatus;
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
            cachingInfo.updateSalary(salaryList, company.getId());
        } else{
            //dept of a company
            Department department = departmentRepo.getDeptByCompId(companyId, salaryUpdate.getDepartmentName());
            List<Salary> salaryList = salaryRepo.salaryListCompDept(companyId, department.getId());
            if (salaryUpdate.getSubType().equals("0")) {
                //update by amount
                for (int i = 0; i < salaryList.size(); i++) {
                    salaryList.get(i).setSalary(salaryList.get(i).getSalary() + salaryUpdate.getValue());
                }
            } else {
                //update by percentage
                for (int i = 0; i < salaryList.size(); i++) {
                    double val = (salaryUpdate.getValue() / (double) 100) * salaryList.get(i).getSalary();
                    salaryList.get(i).setSalary(salaryList.get(i).getSalary() + val);
                }
            }
            cachingInfo.updateSalary(salaryList, company.getId());
        }
        return stringConstant.updateStatus;
    }


}
