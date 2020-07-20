package com.assignment.application.repo;

import com.assignment.application.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepo extends JpaRepository<Salary,Long> {

    @Query("Select sal from Salary sal where sal.employeeId=?1")
    Salary getSalaryById(String empId);

    @Query("Select sal from Salary sal where sal.companyId=?1")
    List<Salary> salaryListComp(Long compId);

    @Query("Select sal from Salary sal where sal.companyId=?1 and sal.departmentId=?2")
    List<Salary> salaryListCompDept(Long compId,Long deptId);
}
