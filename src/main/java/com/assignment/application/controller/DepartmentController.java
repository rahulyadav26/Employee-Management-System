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
@RequestMapping(value="")
public class DepartmentController {

    @Autowired
    private DepartmentServiceInterface departmentServiceInterface;

    @RequestMapping(value="/{company_id}/department" , method = RequestMethod.POST)
    public ResponseEntity<Department> addDepartment(@PathVariable("company_id") long companyId,
                                                    @RequestBody Department deaprtment){
        return departmentServiceInterface.addDepartment(companyId,deaprtment);
    }

    @RequestMapping(value="/department" , method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getDepartments(){
        return departmentServiceInterface.getDepartments();
    }

    @RequestMapping(value="/{company_id}/department/{id}" , method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartment(@PathVariable("id") long id,
                                                    @PathVariable("company_id") long companyId){
        return departmentServiceInterface.getDepartment(companyId,id);
    }

    @RequestMapping(value="/{company_id}/department/{id}/head-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateHead(@PathVariable("id") long id,
                                             @PathVariable("company_id") long companyId,
                                             @RequestBody DeptHeadUpdate deptHeadUpdate){
        return departmentServiceInterface.updateDeptHead(companyId,id,deptHeadUpdate);
    }

    @RequestMapping(value="/{company_id}/department/{id}/employee-count" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateEmployeeCount(@PathVariable("id") long id,
                                                      @PathVariable("company_id") long companyId,
                                                      @RequestBody EmpCountUpdate empCountUpdate){
        return departmentServiceInterface.updateDeptEmpCount(companyId,id,empCountUpdate);
    }

    @RequestMapping(value="/{company_id}/department/{id}/ongoing-project-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateOngoingProject(@PathVariable("id") long id,
                                                       @PathVariable("company_id") long companyId,
                                                       @RequestBody OngoingProjectUpdate ongoingProjectUpdate){
        return departmentServiceInterface.updateDeptOngoingProj(companyId,id,ongoingProjectUpdate);
    }

    @RequestMapping(value="/{company_id}/department/{id}/complete-project-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateCompletedProject(@PathVariable("id") long id,
                                                         @PathVariable("company_id") long companyId,
                                                         CompletedProjectUpdate completedProjectUpdate){
        return departmentServiceInterface.updateDeptCompletedProj(companyId,id, completedProjectUpdate);
    }

    @RequestMapping(value="/{company_id}/department/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") long id,
                                                   @PathVariable("company_id") long companyId){
        return departmentServiceInterface.deleteDepartment(companyId,id);
    }

}
