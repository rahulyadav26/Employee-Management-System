package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Department;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentServiceI {

    Department addDepartment(Long companyId,Department department);

    List<Department> getDepartments();

    Department getDepartment(Long companyId,Long id);

    String updateDepartmentInfo(Long companyId, Long id, DepartmentInfoUpdate departmentInfoUpdate);

    String deleteDepartmentOfCompany(Long id, Long companyId);

}
