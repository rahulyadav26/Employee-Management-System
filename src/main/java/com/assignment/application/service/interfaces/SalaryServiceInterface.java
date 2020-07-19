package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Salary;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SalaryServiceInterface {

    ResponseEntity<Salary> addSalary(long compId,String empId,Salary salary);

    ResponseEntity<Salary> getSalary(long compId,String empId);

    ResponseEntity<List<Salary>> getSalaryList();

    ResponseEntity<String> updateSalary(long compId, SalaryUpdate salaryUpdate);

}
