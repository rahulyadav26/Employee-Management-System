package com.assignment.application.controller;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.authenticator.VerifyUser;
import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceI employeeServiceI;


    @Autowired
    private VerifyUser verifyUser;

    public final String EMPLOYEE_INFORMATION_TOPIC = "EmployeeInformation";

    @PostMapping(value = "/{company_id}/employee")
    public ResponseEntity<Employee> addEmployee(@PathVariable("company_id") Long companyId,
                                                @RequestBody Employee employee,
                                                @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/employee", "post");
        Employee employeeToBeAdded = employeeServiceI.addEmployee(companyId, employee);
        return new ResponseEntity<>(employeeToBeAdded, HttpStatus.OK);

    }

    @GetMapping(value = "{company_id}/employee")
    public ResponseEntity<List<Employee>> getEmployeesOfComp(@PathVariable("company_id") Long companyId,
                                                             @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/employee", "get");
        List<Employee> employeeList = employeeServiceI.getEmployeesOfComp(companyId);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<Employee>> getEmployees(@RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, "employee", "get");
        List<Employee> employeeList = employeeServiceI.getEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    @PatchMapping(value = "/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                     @PathVariable("company_id") Long companyId,
                                                     @RequestBody EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/" + employeeId + "/update-employee-info", "patch");
        employeeServiceI.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId,
                                                 @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/" + empId, "delete");
        employeeServiceI.deleteEmployee(companyId, empId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

}
