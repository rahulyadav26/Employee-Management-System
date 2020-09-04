package com.assignment.application.repo;

import com.assignment.application.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    @Query("Select dept from Department dept where dept.companyId=?1 and dept.isActive=1")
    Page<Department> getDepartmentOfCompany(Long companyId, Pageable pageable);

    @Query("Select dept.id from Department dept where dept.companyId=?1 and dept.isActive=1")
    List<Long> getDepartmentOfCompany(Long companyId);

    @Query("Select dept from Department dept where dept.companyId=?1 and dept.departmentId=?2")
    Department getDepartmentOfCompany(Long companyId, Long departmentId);

    @Query("Select dept from Department dept where dept.isActive=1")
    List<Department> findAll();
}
