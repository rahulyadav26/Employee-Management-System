package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.EmployeeDTO;
import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeService;
import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.util.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VerifyUsers verifyUsers;

    @Autowired
    private EmployeeUtil employeeUtil;

    @PostMapping(value = "/{company_id}/employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@PathVariable("company_id") Long companyId,
                                                   @RequestBody EmployeeDTO employeeDTO,
                                                   @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/employee", "post");
        Employee employee = employeeUtil.convertToEntity(employeeDTO);
        employee = employeeService.addEmployee(companyId, employee);
        return new ResponseEntity<>(employeeUtil.convertToDTO(employee), HttpStatus.OK);

    }

    @GetMapping(value = "{company_id}/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOfComp(@PathVariable("company_id") Long companyId,
                                                                @RequestHeader("access_token") String token,
                                                                Pageable pageable) {
        verifyUsers.authorizeUser(token, companyId + "/employee", "get");
        Page<Employee> employeeList = employeeService.getEmployeesOfComp(companyId, pageable);
        return new ResponseEntity<>(
                employeeList.stream().map(employee -> employeeUtil.convertToDTO(employee)).collect(Collectors.toList()),
                HttpStatus.OK);

    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestHeader("access_token") String token,
                                                          Pageable pageable) {
        verifyUsers.authorizeUser(token, "employee", "get");
        Page<Employee> employeeList = employeeService.getEmployees(pageable);
        List<EmployeeDTO> employeeDTOS =
                employeeList.stream().map(employee -> employeeUtil.convertToDTO(employee)).collect(Collectors.toList());
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);

    }

    @PatchMapping(value = "/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                     @PathVariable("company_id") Long companyId,
                                                     @RequestBody EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/update-employee-info", "patch");
        employeeService.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId,
                                                 @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/" + empId, "delete");
        employeeService.deleteEmployee(companyId, empId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

}
