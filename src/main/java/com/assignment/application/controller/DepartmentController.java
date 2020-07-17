package com.assignment.application.controller;

import com.assignment.application.entity.Department;
import com.assignment.application.service.interfaces.DepartmentServiceInterface;
import com.assignment.application.update.department.CompletedProjectUpdate;
import com.assignment.application.update.department.DeptHeadUpdate;
import com.assignment.application.update.department.EmpCountUpdate;
import com.assignment.application.update.department.OngoingProjectUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/department")
public class DepartmentController {

    @Autowired
    private DepartmentServiceInterface departmentServiceInterface;

    @RequestMapping(value="" , method = RequestMethod.POST)
    public ResponseEntity<Department> addDepartment(@RequestBody Department deaprtment){
        return departmentServiceInterface.addDepartment(deaprtment);
    }

    @RequestMapping(value="" , method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getDepartments(){
        return departmentServiceInterface.getDepartments();
    }

    @RequestMapping(value="/{id}" , method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartment(@PathVariable("id") long id){
        return departmentServiceInterface.getDepartment(id);
    }

    @RequestMapping(value="/{id}/head-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateHead(@PathVariable("id") long id, @RequestBody DeptHeadUpdate deptHeadUpdate){
        return departmentServiceInterface.updateDeptHead(id,deptHeadUpdate);
    }

    @RequestMapping(value="/{id}/employee-count" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateEmployeeCount(@PathVariable("id") long id, @RequestBody EmpCountUpdate empCountUpdate){
        return departmentServiceInterface.updateDeptEmpCount(id,empCountUpdate);
    }

    @RequestMapping(value="/{id}/ongoing-project-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateOngoingProject(@PathVariable("id") long id, @RequestBody OngoingProjectUpdate ongoingProjectUpdate){
        return departmentServiceInterface.updateDeptOngoingProj(id,ongoingProjectUpdate);
    }

    @RequestMapping(value="/{id}/complete-project-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateCompletedProject(@PathVariable("id") long id, CompletedProjectUpdate completedProjectUpdate){
        return departmentServiceInterface.updateDeptCompletedProj(id, completedProjectUpdate);
    }

    @RequestMapping(value="/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") long id){
        return departmentServiceInterface.deleteDepartment(id);
    }

}
