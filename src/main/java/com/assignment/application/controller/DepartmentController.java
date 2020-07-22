package com.assignment.application.controller;

import com.assignment.application.entity.Department;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.service.interfaces.DepartmentServiceI;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentServiceI departmentServiceI;

    @Autowired
    private VerifyUser verifyUser;

    public final String updateStatus = "Update Successful";

    @PostMapping(value = "/{company_id}/department")
    public ResponseEntity<Department> addDepartment(@PathVariable("company_id") Long companyId,
                                                    @RequestBody Department department,
                                                    @RequestHeader("username") String username,
                                                    @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Department departmentToBeAdded = departmentServiceI.addDepartment(companyId, department);
        if (departmentToBeAdded == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(departmentToBeAdded, HttpStatus.OK);

    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<Department>> getDepartments(@RequestHeader("username") String username,
                                                           @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(departmentServiceI.getDepartments(), HttpStatus.OK);

    }

    @GetMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long id,
                                                    @PathVariable("company_id") Long companyId,
                                                    @RequestHeader("username") String username,
                                                    @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Department department = departmentServiceI.getDepartment(companyId, id);
        if (department == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);

    }

    @PatchMapping(value = "/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") Long id,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentInfoUpdate departmentInfoUpdate,
                                                       @RequestHeader("username") String username,
                                                       @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if(departmentServiceI.updateDepartmentInfo(companyId,id,departmentInfoUpdate).equals(updateStatus)){
            return new ResponseEntity<>(updateStatus,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Request",HttpStatus.BAD_REQUEST);

    }

}
