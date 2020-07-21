package com.assignment.application.controller;

import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceI employeeServiceI;

    @Autowired
    private KafkaTemplate<String,EmployeeInfoUpdate> kafkaTemplateEmployeeUpdate;

    @Autowired
    private KafkaTemplate<String,Employee> kafkaTemplateEmployee;

    @Autowired
    private VerifyUser verifyUser;

    public final String TOPIC="EmployeeInformation";

    @PostMapping(value="/{company_id}/employee")
    public ResponseEntity<Employee> addEmployee(@PathVariable("company_id") Long companyId,
                                                @RequestBody Employee employee,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            kafkaTemplateEmployee.send(TOPIC, employee);
            return employeeServiceI.addEmployee(companyId, employee);
        }
    }

    @GetMapping(value="{company_id}/employee")
    public ResponseEntity<List<Employee>> getEmployeesOfComp(@PathVariable("company_id") Long companyId,
                                                             @RequestHeader("username") String username,
                                                             @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return employeeServiceI.getEmployeesOfComp(companyId);
        }
    }

    @GetMapping(value="/employee")
    public ResponseEntity<List<Employee>> getEmployees(@RequestHeader("username") String username,
                                                       @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return employeeServiceI.getEmployees();
        }
    }

    @PatchMapping(value="/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                     @PathVariable("company_id") Long companyId,
                                                     @RequestBody EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader("username") String username,
                                                     @RequestHeader("password") String password){
        int status = 0;
        System.out.println("username and password " + username + " " + password);
        if(verifyUser.authorizeUser(username,password)==1 || (verifyUser.authorizeEmployee(username,password)==1 && employeeId.equalsIgnoreCase(username))){
            status=1;
        }
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            kafkaTemplateEmployeeUpdate.send(TOPIC, employeeInfoUpdate);
            return employeeServiceI.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate);
        }
    }

    @DeleteMapping(value="{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId,
                                                 @RequestHeader("username") String username,
                                                 @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return employeeServiceI.deleteEmployee(companyId, empId);
        }
    }

}
