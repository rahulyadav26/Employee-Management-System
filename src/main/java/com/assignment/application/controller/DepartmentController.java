package com.assignment.application.controller;

import com.assignment.application.Constants.StringConstant;
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

    @Autowired
    private StringConstant stringConstants;

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
        if(departmentServiceI.updateDepartmentInfo(companyId,id,departmentInfoUpdate).equals(stringConstants.updateStatus)){
            return new ResponseEntity<>(stringConstants.updateStatus,HttpStatus.OK);
        }
        return new ResponseEntity<>(stringConstants.invalidStatus,HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<String> deleteDepartmentOfCompany(@PathVariable("id") Long id,
                                                            @PathVariable("company_id") Long companyId,
                                                            @RequestHeader("username") String username,
                                                            @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if(departmentServiceI.deleteDepartmentOfCompany(id,companyId).equalsIgnoreCase(stringConstants.deleteStatus)){
            return new ResponseEntity<>(stringConstants.deleteStatus,HttpStatus.OK);
        }
        return new ResponseEntity<>(stringConstants.invalidStatus,HttpStatus.BAD_REQUEST);
    }

}
