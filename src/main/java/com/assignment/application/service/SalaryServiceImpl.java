package com.assignment.application.service;

import com.assignment.application.entity.Company;
import com.assignment.application.repo.CompanyRepo;
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

    @Override
    public Salary addSalary(Long companyID, String employeeID, Salary salary) {
        if (salary == null || !salary.getEmployeeId().equals(employeeID) || !salary.getCompanyId().equals(companyID) || salary.getId().equals(0)) {
            return null;
        }
        return salaryRepo.save(salary);
    }

    @Override
    public Salary getSalary(Long companyId, String employeeId) {
        Salary salary = salaryRepo.getSalaryById(employeeId);
        if (salary == null) {
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
        Company company = companyRepo.getCompany(companyId);
        if (company == null || salaryUpdate == null) {
            return "Invalid Credentials";
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
        } else if (salaryUpdate.getType().equals("1")) {
            //dept of a company
            Department department = departmentRepo.getDeptByCompId(companyId, salaryUpdate.getDept_name());
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
        return "Salary Updated";
    }


}
