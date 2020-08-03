package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.EmployeeDTO;
import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceI employeeServiceI;

    @Autowired
    private VerifyUsers verifyUsers;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{company_id}/employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@PathVariable("company_id") Long companyId,
                                                   @RequestBody EmployeeDTO employeeDTO,
                                                   @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/employee", "post");
        Employee employee = convertToEntity(employeeDTO);
        employee = employeeServiceI.addEmployee(companyId, employee);
        employeeDTO = convertToDTO(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);

    }

    @GetMapping(value = "{company_id}/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOfComp(@PathVariable("company_id") Long companyId,
                                                                @RequestHeader("access_token") String token,
                                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        verifyUsers.authorizeUser(token, companyId + "/employee", "get");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Employee> employeeList = employeeServiceI.getEmployeesOfComp(companyId, pageable);
        return new ResponseEntity<>(employeeList.stream().map(this::convertToDTO).collect(Collectors.toList()),
                                    HttpStatus.OK);

    }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestHeader("access_token") String token,
                                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        verifyUsers.authorizeUser(token, "employee", "get");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Employee> employeeList = employeeServiceI.getEmployees(pageable);
        List<EmployeeDTO> employeeDTOS = employeeList.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);

    }

    @PatchMapping(value = "/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                     @PathVariable("company_id") Long companyId,
                                                     @RequestBody EmployeeInfoUpdate employeeInfoUpdate,
                                                     @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/update-employee-info", "patch");
        employeeServiceI.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId,
                                                 @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/" + empId, "delete");
        employeeServiceI.deleteEmployee(companyId, empId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        String dob = new String(Base64.getDecoder().decode(employee.getDob()));
        employeeDTO.setDob(LocalDate.parse(dob));
        return employeeDTO;
    }

}
