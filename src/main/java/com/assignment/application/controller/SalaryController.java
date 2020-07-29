package com.assignment.application.controller;


import com.assignment.application.Constants.StringConstant;
import com.assignment.application.authenticator.VerifyUser;
import com.assignment.application.entity.Salary;
import com.assignment.application.service.interfaces.SalaryServiceI;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryController {

    @Autowired
    private SalaryServiceI salaryServiceI;


    @Autowired
    private VerifyUser verifyUser;



    @PostMapping(value = "{comp_id}/{emp_id}/salary")
    public ResponseEntity<Salary> addSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String employeeId,
                                                @RequestBody Salary salary,
                                                @RequestHeader("access_token") String token) {
        try {
            if (verifyUser.authorizeUser(token,companyId+"/"+employeeId+"/salary","post") == 1) {
                Salary salaryToBeAdded = salaryServiceI.addSalary(companyId, employeeId, salary);
                if (salary == null) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(salaryToBeAdded, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "{comp_id}/{emp_id}/salary")
    public ResponseEntity<Salary> getSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String employeeId,
                                                @RequestHeader("access_token") String token) {
        try {
            if (verifyUser.authorizeUser(token,companyId+"/"+employeeId+"/salary","get") == 1) {
                Salary salary = salaryServiceI.getSalary(companyId, employeeId);
                if (salary == null) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(salary, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/salary")
    public ResponseEntity<List<Salary>> getSalaryList(@RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,"/salary","get");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            List<Salary> salaryList = salaryServiceI.getSalaryList();
            if (salaryList == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(salaryList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "{comp_id}/salary-update")
    public ResponseEntity<String> updateSalary(@PathVariable("comp_id") Long companyId,
                                               @RequestBody SalaryUpdate salaryUpdate,
                                               @RequestHeader("access_token") String token){
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/salary-update","patch");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            if (salaryServiceI.updateSalary(companyId, salaryUpdate).equalsIgnoreCase(StringConstant.UPDATE_SUCCESSFUL)) {
                return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
            }
            return new ResponseEntity<>(StringConstant.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
