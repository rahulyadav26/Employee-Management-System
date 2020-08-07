package com.assignment.application.repo;

import com.assignment.application.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepo extends JpaRepository<Salary, Long> {

    Page<Salary> getSalaryByEmployeeId(String empId, Pageable pageable);

    @Query("Select sal from Salary sal where sal.employeeId=?1")
    List<Salary> getSalaryByEmployeeId(String empId);

    @Query("Select sal from Salary sal where sal.updatedAt is null")
    List<Salary> findAll();

    @Query("Select sal from Salary sal where sal.employeeId=?1 and sal.updatedAt is null")
    Salary getCurrentSalaryById(String empId);
}
