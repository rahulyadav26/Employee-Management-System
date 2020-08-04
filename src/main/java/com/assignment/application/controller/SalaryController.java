package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.SalaryDTO;
import com.assignment.application.entity.Salary;
import com.assignment.application.service.interfaces.SalaryService;
import com.assignment.application.update.SalaryEmployeeUpdate;
import com.assignment.application.update.SalaryUpdate;
import com.assignment.application.util.SalaryUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private VerifyUsers verifyUsers;

    @Autowired
    private SalaryUtil salaryUtil;

    @PostMapping(value = "{comp_id}/{emp_id}/salary")
    public ResponseEntity<SalaryDTO> addSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                   @PathVariable("emp_id") String employeeId,
                                                   @RequestBody SalaryDTO salaryDTO,
                                                   @RequestHeader("access_token") String token) {

        verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/salary", "post");
        Salary salary = salaryUtil.convertToEntity(salaryDTO);
        salary = salaryService.addSalary(companyId, employeeId, salary);
        return new ResponseEntity<>(salaryUtil.convertToDTO(salary), HttpStatus.OK);
    }

    @GetMapping(value = "{comp_id}/{emp_id}/salary")
    public ResponseEntity<List<SalaryDTO>> getSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                         @PathVariable("emp_id") String employeeId,
                                                         @RequestHeader("access_token") String token,
                                                         Pageable pageable) {
        verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/salary", "get");
        Page<Salary> salary = salaryService.getSalary(companyId, employeeId, pageable);
        return new ResponseEntity<>(salary.stream().map(salaryEntity -> salaryUtil.convertToDTO(salaryEntity)).collect(Collectors.toList()),
                                    HttpStatus.OK);
    }

    @GetMapping(value = "/salary")
    public ResponseEntity<List<SalaryDTO>> getSalaryList(@RequestHeader("access_token") String token,
                                                         Pageable pageable) {
        verifyUsers.authorizeUser(token, "/salary", "get");
        Page<Salary> salaryList = salaryService.getSalaryList(pageable);
        return new ResponseEntity<>(salaryList.stream().map(salary -> salaryUtil.convertToDTO(salary)).collect(Collectors.toList()),
                                    HttpStatus.OK);
    }

    //part of deploayble set 3
    @PatchMapping(value = "{comp_id}/salary-update")
    public ResponseEntity<String> updateSalary(@PathVariable("comp_id") Long companyId,
                                               @RequestBody SalaryUpdate salaryUpdate,
                                               @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/salary-update", "patch");
        salaryService.updateSalary(companyId, salaryUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @PatchMapping(value = "{comp_id}/{emp_id}/update-salary")
    public ResponseEntity<String> updateSalaryOfEmployee(@PathVariable("comp_id") Long companyId,
                                                         @PathVariable("emp_id") String employeeId,
                                                         @RequestBody SalaryEmployeeUpdate salaryEmployeeUpdate,
                                                         @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/update-salary", "patch");
        salaryService.updateSalaryOfEmployee(companyId, employeeId, salaryEmployeeUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

}
