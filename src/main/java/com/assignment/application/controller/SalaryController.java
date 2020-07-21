package com.assignment.application.controller;


import com.assignment.application.entity.Salary;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.service.interfaces.SalaryServiceI;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryController {

    @Autowired
    private SalaryServiceI salaryServiceI;

    @Autowired
    private KafkaTemplate<String,SalaryUpdate> kafkaTemplateSalary;

    @Autowired
    private VerifyUser verifyUser;

    public final String TOPIC = "SalaryUpdate";

    @PostMapping(value="{comp_id}/{emp_id}/salary" )
    public ResponseEntity<Salary> addSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String employeeId,
                                                @RequestBody Salary salary,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password){
        int status = 0;
        if(verifyUser.authorizeUser(username,password)==1 || (verifyUser.authorizeEmployee(username,password)==1 && employeeId.equalsIgnoreCase(username))){
            status=1;
        }
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else
        return salaryServiceI.addSalary(companyId,employeeId,salary);
    }

    @GetMapping(value="{comp_id}/{emp_id}/salary" )
    public ResponseEntity<Salary> getSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String employeeId,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password){
        int status = 0;
        if(verifyUser.authorizeUser(username,password)==1 || (verifyUser.authorizeEmployee(username,password)==1 && employeeId.equalsIgnoreCase(username))){
            status=1;
        }
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return salaryServiceI.getSalary(companyId, employeeId);
        }
    }

    @GetMapping(value = "/salary")
    public ResponseEntity<List<Salary>> getSalaryList(@RequestHeader("username") String username,
                                                      @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return salaryServiceI.getSalaryList();
        }
    }

    @PatchMapping(value = "{comp_id}/salary-update")
    public ResponseEntity<String> updateSalary(@PathVariable("comp_id") Long companyId,
                                               @RequestBody SalaryUpdate salaryUpdate,
                                               @RequestHeader("username") String username,
                                               @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            kafkaTemplateSalary.send(TOPIC, salaryUpdate);
            return salaryServiceI.updateSalary(companyId, salaryUpdate);
        }
    }

}
