package com.assignment.application.repo;

import com.assignment.application.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepo extends JpaRepository<Salary,Long> {

    @Query("Select sal from Salary sal where sal.emp_id=?1")
    Salary getSalaryById(String empId);

    @Query("Select sal from Salary sal where sal.comp_id=?1")
    List<Salary> salaryListComp(long compId);

    @Query("Select sal from Salary sal where sal.comp_id=?1 and sal.dept_id=?2")
    List<Salary> salaryListCompDept(long compId,long deptId);
}
