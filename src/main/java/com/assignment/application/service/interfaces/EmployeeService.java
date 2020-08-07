package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Employee;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeService {

    Employee addEmployee(Long id, Employee employee, String userId);

    Page<Employee> getEmployeesOfComp(Long companyId, Pageable pageable);

    Page<Employee> getEmployees(Pageable pageable);

    String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate, String userId);

    String deleteEmployee(Long companyId, String employeeId, String userId);

}
