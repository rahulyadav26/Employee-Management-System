package com.assignment.application.repo;

import com.assignment.application.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepo extends JpaRepository<Salary, Long> {

    //@Query("Select sal from Salary sal where sal.employeeId=?1")
    Page<Salary> getSalaryByEmployeeId(String empId, Pageable pageable);

    @Query("Select sal from Salary sal where sal.employeeId=?1")
    List<Salary> getSalaryByEmployeeId(String empId);

    @Query(value = "Select sal from Salary sal where sal.companyId=?1", nativeQuery = true)
    List<Salary> salaryListComp(Long compId);

    @Query(value = "Select sal from Salary sal where sal.companyId=?1 and sal.departmentId=?2", nativeQuery = true)
    List<Salary> salaryListCompDept(Long compId, Long deptId);

    @Query("Select sal from Salary sal where sal.isCurrent=1")
    List<Salary> findAll();

    @Query("Select sal from Salary sal where sal.employeeId=?1 and sal.isCurrent=1")
    Salary getCurrentSalaryById(String empId);
}
