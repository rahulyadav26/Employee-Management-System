package com.assignment.application.repo;

import com.assignment.application.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(value = "Select emp from Employee emp where emp.employeeId = ?1")
    Employee getEmployee(String employeeId);

    @Query(value = "Select emp from Employee emp where emp.uniqueId=?1")
    Employee getEmployeeByUniqueId(String uniqueId);

    @Query(value = "Select emp from Employee emp where emp.departmentId IN :list and emp.isActive=1")
    Page<Employee> getEmployeesOfCompany(List<Long> list, Pageable pageable);

    @Query(value = "Select emp from Employee emp where emp.departmentId IN :list and emp.isActive=1")
    List<Employee> getEmployeesOfCompany(List<Long> list);

}
