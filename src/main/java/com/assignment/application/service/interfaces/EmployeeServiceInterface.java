package com.assignment.application.service.interfaces;

import com.assignment.application.update.employee.AddressUpdate;
import com.assignment.application.entity.Employee;
import com.assignment.application.update.employee.PositionUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeServiceInterface {

    ResponseEntity<Employee> addEmployee(long id, Employee employee);

    ResponseEntity<List<Employee>> getEmployeesOfComp(long comp_id);

    ResponseEntity<List<Employee>> getEmployees();

    ResponseEntity<String> updateAddress(String emp_id, long companyId, AddressUpdate addressUpdate);

    ResponseEntity<String> updatePosition(String emp_id, long companyId, PositionUpdate positionUpdate);

}
