package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Department;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentServiceI {

    ResponseEntity<Department> addDepartment(Long companyId,Department department);

    ResponseEntity<List<Department>> getDepartments();

    ResponseEntity<Department> getDepartment(Long companyId,Long id);

    ResponseEntity<String> updateDepartmentInfo(Long companyId, Long id, DepartmentInfoUpdate departmentInfoUpdate);

}
