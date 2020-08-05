package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.EmployeeDTO;
import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeService;
import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.util.EmployeeUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<EmployeeDTO> addEmployee(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                   @RequestBody @Valid EmployeeDTO employeeDTO,
                                                   @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUsers.authorizeUser(token, companyId + StringConstant.EMPLOYEE, StringConstant.POST);
        Employee employee = employeeUtil.convertToEntity(employeeDTO);
        employee = employeeService.addEmployee(companyId, employee, userId);
        return new ResponseEntity<>(employeeUtil.convertToDTO(employee), HttpStatus.OK);

    }

    @GetMapping(value = "{company_id}/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOfComp(
            @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
            @RequestHeader(StringConstant.ACCESS_TOKEN) String token,
            Pageable pageable) {
        verifyUsers.authorizeUser(token, companyId + StringConstant.EMPLOYEE, StringConstant.GET);
        Page<Employee> employeeList = employeeService.getEmployeesOfComp(companyId, pageable);
        return new ResponseEntity<>(
                employeeList.stream().map(employee -> employeeUtil.convertToDTO(employee)).collect(Collectors.toList()),
                HttpStatus.OK);

    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestHeader(StringConstant.ACCESS_TOKEN) String token,
                                                          Pageable pageable) {
        verifyUsers.authorizeUser(token, StringConstant.EMPLOYEE, StringConstant.GET);
        Page<Employee> employeeList = employeeService.getEmployees(pageable);
        List<EmployeeDTO> employeeDTOS =
                employeeList.stream().map(employee -> employeeUtil.convertToDTO(employee)).collect(Collectors.toList());
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);

    }

    @PatchMapping(value = "/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") @NonNull String employeeId,
                                                     @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                     @RequestBody @Valid EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/update-employee-info",
                                                  StringConstant.PATCH);
        employeeService.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate, userId);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                 @PathVariable("emp_id") @Valid String empId,
                                                 @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUsers.authorizeUser(token, companyId + "/" + empId, StringConstant.DELETE);
        employeeService.deleteEmployee(companyId, empId, userId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

}
