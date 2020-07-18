package com.assignment.application.repo;

import com.assignment.application.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    @Query("Select emp from Employee emp where emp.comp_id = ?1")
    List<Employee> getAllEmpByCompId(long comp_id);

    @Query("Select emp from Employee emp where emp.emp_id = ?1")
    Employee getEmployee(String emp_id);

}
