package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Salary;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SalaryServiceI {

    Salary addSalary(Long companyId,String employeeId,Salary salary);

    Salary getSalary(Long companyId,String employeeId);

    List<Salary> getSalaryList();

    String updateSalary(Long companyId, SalaryUpdate salaryUpdate);

}
