package com.assignment.application.controller;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Employee;
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
    private KafkaTemplate<String, EmployeeInfoUpdate> kafkaTemplateEmployeeUpdate;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    @Autowired
    private StringConstant stringConstant;

    @Autowired
    private VerifyUser verifyUser;

    public final String TOPIC = "EmployeeInformation";

    @PostMapping(value = "/{company_id}/employee")
    public ResponseEntity<Employee> addEmployee(@PathVariable("company_id") Long companyId,
                                                @RequestBody Employee employee,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Employee employeeToBeAdded = employeeServiceI.addEmployee(companyId, employee);
        if (employeeToBeAdded == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        kafkaTemplateEmployee.send(TOPIC, employee);
        return new ResponseEntity<>(employeeToBeAdded, HttpStatus.OK);
    }

    @GetMapping(value = "{company_id}/employee")
    public ResponseEntity<List<Employee>> getEmployeesOfComp(@PathVariable("company_id") Long companyId,
                                                             @RequestHeader("username") String username,
                                                             @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        List<Employee> employeeList = employeeServiceI.getEmployeesOfComp(companyId);
        if (employeeList == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<Employee>> getEmployees(@RequestHeader("username") String username,
                                                       @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        List<Employee> employeeList = employeeServiceI.getEmployees();
        if (employeeList == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PatchMapping(value = "/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                     @PathVariable("company_id") Long companyId,
                                                     @RequestBody EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader("username") String username,
                                                     @RequestHeader("password") String password) {
        int status = 0;
        if (verifyUser.authorizeUser(username, password) == 1 || (verifyUser.authorizeEmployee(username, password) == 1 && employeeId.equalsIgnoreCase(username))) {
            if (employeeServiceI.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate).equalsIgnoreCase(stringConstant.updateStatus)) {
                kafkaTemplateEmployeeUpdate.send(TOPIC, employeeInfoUpdate);
                return new ResponseEntity<>(stringConstant.updateStatus, HttpStatus.OK);
            }
            return new ResponseEntity<>(stringConstant.invalidStatus, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(value = "{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId,
                                                 @RequestHeader("username") String username,
                                                 @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (employeeServiceI.deleteEmployee(companyId, empId).equalsIgnoreCase(stringConstant.deleteStatus)) {
            return new ResponseEntity<>(stringConstant.deleteStatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(stringConstant.invalidStatus, HttpStatus.BAD_REQUEST);

    }

}
