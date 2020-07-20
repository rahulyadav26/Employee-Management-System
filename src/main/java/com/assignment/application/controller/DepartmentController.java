package com.assignment.application.controller;

import com.assignment.application.entity.Department;
import com.assignment.application.service.interfaces.DepartmentServiceI;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentServiceI departmentServiceI;

    @PostMapping(value="/{company_id}/department")
    public ResponseEntity<Department> addDepartment(@PathVariable("company_id") Long companyId,
                                                    @RequestBody Department department){
        return departmentServiceI.addDepartment(companyId,department);
    }

    @GetMapping(value="/department")
    public ResponseEntity<List<Department>> getDepartments(){
        return departmentServiceI.getDepartments();
    }

    @GetMapping(value="/{company_id}/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long id,
                                                    @PathVariable("company_id") Long companyId){
        return departmentServiceI.getDepartment(companyId,id);
    }

    @PatchMapping(value="/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") Long id,
                                             @PathVariable("company_id") Long companyId,
                                             @RequestBody DepartmentInfoUpdate departmentInfoUpdate){
        return departmentServiceI.updateDepartmentInfo(companyId,id, departmentInfoUpdate);
    }

}
