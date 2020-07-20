package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Salary;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SalaryServiceInterface {

    ResponseEntity<Salary> addSalary(Long companyId,String employeeId,Salary salary);

    ResponseEntity<Salary> getSalary(Long companyId,String employeeId);

    ResponseEntity<List<Salary>> getSalaryList();

    ResponseEntity<String> updateSalary(Long companyId, SalaryUpdate salaryUpdate);

}
