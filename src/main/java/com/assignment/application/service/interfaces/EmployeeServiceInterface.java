package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeServiceInterface {

    ResponseEntity<Employee> addEmployee(long id, Employee employee);

    ResponseEntity<List<Employee>> getEmployeesOfComp(long comp_id);

    ResponseEntity<List<Employee>> getEmployees();

}
