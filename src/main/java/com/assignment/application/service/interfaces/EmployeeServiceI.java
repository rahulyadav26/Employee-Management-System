package com.assignment.application.service.interfaces;

import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeServiceI {

    ResponseEntity<Employee> addEmployee(Long id, Employee employee);

    ResponseEntity<List<Employee>> getEmployeesOfComp(Long companyId);

    ResponseEntity<List<Employee>> getEmployees();

    ResponseEntity<String> updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate);

    ResponseEntity<String> deleteEmployee(Long companyId,String employeeId);

}
