package com.assignment.application.controller;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.authenticator.VerifyUser;
import com.assignment.application.entity.Department;
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

    @PostMapping(value = "/{company_id}/department")
    public ResponseEntity<Department> addDepartment(@PathVariable("company_id") Long companyId,
                                                    @RequestBody Department department,
                                                    @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department", "post");
        Department departmentToBeAdded = departmentServiceI.addDepartment(companyId, department);
        return new ResponseEntity<>(departmentToBeAdded, HttpStatus.OK);
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<Department>> getDepartments(@RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, "department", "get");
        return new ResponseEntity<>(departmentServiceI.getDepartments(), HttpStatus.OK);
    }

    @GetMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long id,
                                                    @PathVariable("company_id") Long companyId,
                                                    @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id, "get");
        Department department = departmentServiceI.getDepartment(companyId, id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PatchMapping(value = "/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") Long id,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentInfoUpdate departmentInfoUpdate,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id + "/update-department", "patch");
        departmentServiceI.updateDepartmentInfo(companyId, id, departmentInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<String> deleteDepartmentOfCompany(@PathVariable("id") Long id,
                                                            @PathVariable("company_id") Long companyId,
                                                            @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id, "delete");
        departmentServiceI.deleteDepartmentOfCompany(id, companyId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

}
