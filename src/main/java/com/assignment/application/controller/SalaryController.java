package com.assignment.application.controller;


import com.assignment.application.constants.StringConstant;
import com.assignment.application.authenticator.VerifyUser;
import com.assignment.application.entity.Salary;
import com.assignment.application.update.SalaryEmployeeUpdate;
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

        verifyUser.authorizeUser(token, companyId + "/" + employeeId + "/salary", "post");
        Salary salaryToBeAdded = salaryServiceI.addSalary(companyId, employeeId, salary);
        return new ResponseEntity<>(salaryToBeAdded, HttpStatus.OK);
    }

    @GetMapping(value = "{comp_id}/{emp_id}/salary")
    public ResponseEntity<Salary> getSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String employeeId,
                                                @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/" + employeeId + "/salary", "get");
        Salary salary = salaryServiceI.getSalary(companyId, employeeId);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    @GetMapping(value = "/salary")
    public ResponseEntity<List<Salary>> getSalaryList(@RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, "/salary", "get");
        List<Salary> salaryList = salaryServiceI.getSalaryList();
        return new ResponseEntity<>(salaryList, HttpStatus.OK);
    }

    @PatchMapping(value = "{comp_id}/salary-update")
    public ResponseEntity<String> updateSalary(@PathVariable("comp_id") Long companyId,
                                               @RequestBody SalaryUpdate salaryUpdate,
                                               @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/salary-update", "patch");
        salaryServiceI.updateSalary(companyId, salaryUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @PatchMapping(value = "{comp_id}/{emp_id}/update-salary")
    public ResponseEntity<String> updateSalaryOfEmployee(@PathVariable("comp_id") Long companyId,
                                                         @PathVariable("emp_id") String employeeId,
                                               @RequestBody SalaryEmployeeUpdate salaryEmployeeUpdate,
                                               @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId +"/" +employeeId + "/update-salary", "patch");
        salaryServiceI.updateSalaryOfEmployee(companyId,employeeId, salaryEmployeeUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }
}
