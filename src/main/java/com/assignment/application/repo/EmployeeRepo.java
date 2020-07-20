package com.assignment.application.repo;

import com.assignment.application.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    @Query(value = "Select emp from Employee emp where emp.companyId = ?1")
    List<Employee> getAllEmpByCompId(Long companyId);

    @Query(value = "Select emp from Employee emp where emp.employeeId = ?1")
    Employee getEmployee(String employeeId);

}
