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

    ResponseEntity<Department> addDepartment(Department department);

    ResponseEntity<List<Department>> getDepartments();

    ResponseEntity<Department> getDepartment(long id);

    ResponseEntity<String> updateDeptHead(long id,DeptHeadUpdate deptHeadUpdate);

    ResponseEntity<String> updateDeptEmpCount(long id, EmpCountUpdate empCountUpdate);

    ResponseEntity<String> updateDeptOngoingProj(long id, OngoingProjectUpdate ongoingProjectUpdate);

    ResponseEntity<String> updateDeptCompletedProj(long id, CompletedProjectUpdate completedProjectUpdate);

    ResponseEntity<String> deleteDepartment(long id);

}
