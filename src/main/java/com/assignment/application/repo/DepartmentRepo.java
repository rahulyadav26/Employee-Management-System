package com.assignment.application.repo;

import com.assignment.application.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepo extends JpaRepository<Department,Long> {

    @Query("Select dept from Department dept where dept.companyId=?1 and Upper(dept.name)=?2")
    Department getDeptByCompId(Long companyId,String deptName);

}
