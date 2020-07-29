package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Employee;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeServiceI {

    Employee addEmployee(Long id, Employee employee);

    List<Employee> getEmployeesOfComp(Long companyId);

    List<Employee> getEmployees();

    String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate);

    String deleteEmployee(Long companyId,String employeeId);

}
