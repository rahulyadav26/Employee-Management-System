package com.assignment.application.controller;

import com.assignment.application.Constants.StringConstant;
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
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/department","post");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            Department departmentToBeAdded = departmentServiceI.addDepartment(companyId, department);
            if (departmentToBeAdded == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(departmentToBeAdded, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<Department>> getDepartments(@RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,"department","get");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(departmentServiceI.getDepartments(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long id,
                                                    @PathVariable("company_id") Long companyId,
                                                    @RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/department/"+id,"get");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            Department department = departmentServiceI.getDepartment(companyId, id);
            if (department == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping(value = "/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") Long id,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentInfoUpdate departmentInfoUpdate,
                                                       @RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/department/"+id+"/update-department","patch");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            if (departmentServiceI.updateDepartmentInfo(companyId, id, departmentInfoUpdate).equals(StringConstant.UPDATE_SUCCESSFUL)) {
                return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
            }
            return new ResponseEntity<>(StringConstant.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<String> deleteDepartmentOfCompany(@PathVariable("id") Long id,
                                                            @PathVariable("company_id") Long companyId,
                                                            @RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/department/"+id,"delete");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            if (departmentServiceI.deleteDepartmentOfCompany(id, companyId).equalsIgnoreCase(StringConstant.DELETION_SUCCESSFUL)) {
                return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
            }
            return new ResponseEntity<>(StringConstant.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
