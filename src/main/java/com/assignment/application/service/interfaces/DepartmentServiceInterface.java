package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Department;
import com.assignment.application.update.department.CompletedProjectUpdate;
import com.assignment.application.update.department.DeptHeadUpdate;
import com.assignment.application.update.department.EmpCountUpdate;
import com.assignment.application.update.department.OngoingProjectUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentServiceInterface {

    ResponseEntity<Department> addDepartment(long companyId,Department department);

    ResponseEntity<List<Department>> getDepartments();

    ResponseEntity<Department> getDepartment(long companyId,long id);

    ResponseEntity<String> updateDeptHead(long companyId,long id,DeptHeadUpdate deptHeadUpdate);

    ResponseEntity<String> updateDeptEmpCount(long companyId,long id, EmpCountUpdate empCountUpdate);

    ResponseEntity<String> updateDeptOngoingProj(long companyId,long id, OngoingProjectUpdate ongoingProjectUpdate);

    ResponseEntity<String> updateDeptCompletedProj(long companyId,long id, CompletedProjectUpdate completedProjectUpdate);

    ResponseEntity<String> deleteDepartment(long companyId,long id);

}
