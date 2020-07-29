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
        try {
            int status = verifyUser.authorizeUser(token, companyId+"/employee","post");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            Employee employeeToBeAdded = employeeServiceI.addEmployee(companyId, employee);
            if (employeeToBeAdded == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(employeeToBeAdded, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "{company_id}/employee")
    public ResponseEntity<List<Employee>> getEmployeesOfComp(@PathVariable("company_id") Long companyId,
                                                             @RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/employee","get");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            List<Employee> employeeList = employeeServiceI.getEmployeesOfComp(companyId);
            if (employeeList == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<Employee>> getEmployees(@RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,"employee","get");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            List<Employee> employeeList = employeeServiceI.getEmployees();
            if (employeeList == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                     @PathVariable("company_id") Long companyId,
                                                     @RequestBody EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader("access_token") String token) {
        try {
            int status = 0;
            if (verifyUser.authorizeUser(token,companyId+"/"+employeeId+"/update-employee-info","patch") == 1) {
                if (employeeServiceI.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate).equalsIgnoreCase(StringConstant.UPDATE_SUCCESSFUL)) {
                    return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
                }
                return new ResponseEntity<>(StringConstant.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId,
                                                 @RequestHeader("access_token") String token) {
        try {
            int status = verifyUser.authorizeUser(token,companyId+"/"+empId,"delete");
            if (status == 0) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            if (employeeServiceI.deleteEmployee(companyId, empId).equalsIgnoreCase(StringConstant.DELETION_SUCCESSFUL)) {
                return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
            }
            return new ResponseEntity<>(StringConstant.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

}
