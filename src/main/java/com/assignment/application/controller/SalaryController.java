package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.SalaryDTO;
import com.assignment.application.entity.Salary;
import com.assignment.application.service.interfaces.SalaryService;
import com.assignment.application.update.SalaryEmployeeUpdate;
import com.assignment.application.update.SalaryUpdate;
import com.assignment.application.util.SalaryUtil;
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
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private VerifyUsers verifyUsers;

    @Autowired
    private SalaryUtil salaryUtil;

    @PostMapping(value = "{company_id}/{emp_id}/salary")
    public ResponseEntity<SalaryDTO> addSalaryInfo(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                   @PathVariable("emp_id") @NonNull String employeeId,
                                                   @RequestBody @Valid SalaryDTO salaryDTO,
                                                   @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {

        String userId = verifyUsers.authorizeUser(token, companyId + "/" + employeeId + StringConstant.SALARY,
                                                  StringConstant.POST);
        Salary salary = salaryUtil.convertToEntity(salaryDTO);
        salary = salaryService.addSalary(companyId, employeeId, salary, userId);
        return new ResponseEntity<>(salaryUtil.convertToDTO(salary), HttpStatus.OK);
    }

    @GetMapping(value = "{company_id}/{emp_id}/salary")
    public ResponseEntity<List<SalaryDTO>> getSalaryInfo(
            @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
            @PathVariable("emp_id") @NonNull String employeeId,
            @RequestHeader(StringConstant.ACCESS_TOKEN) String token,
            Pageable pageable) {
        verifyUsers.authorizeUser(token, companyId + "/" + employeeId + StringConstant.SALARY, StringConstant.GET);
        Page<Salary> salary = salaryService.getSalary(companyId, employeeId, pageable);
        return new ResponseEntity<>(
                salary.stream().map(salaryEntity -> salaryUtil.convertToDTO(salaryEntity)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/salary")
    public ResponseEntity<List<SalaryDTO>> getSalaryList(@RequestHeader(StringConstant.ACCESS_TOKEN) String token,
                                                         Pageable pageable) {
        verifyUsers.authorizeUser(token, StringConstant.SALARY, StringConstant.GET);
        Page<Salary> salaryList = salaryService.getSalaryList(pageable);
        return new ResponseEntity<>(
                salaryList.stream().map(salary -> salaryUtil.convertToDTO(salary)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PatchMapping(value = "{company_id}/salary-update")
    public ResponseEntity<String> updateSalary(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                               @RequestBody @Valid SalaryUpdate salaryUpdate,
                                               @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUsers.authorizeUser(token, companyId + "/salary-update", StringConstant.PATCH);
        salaryService.updateSalary(companyId, salaryUpdate, userId);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @PatchMapping(value = "{company_id}/{emp_id}/update-salary")
    public ResponseEntity<String> updateSalaryOfEmployee(
            @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
            @PathVariable("emp_id") @NonNull String employeeId,
            @RequestBody @Valid SalaryEmployeeUpdate salaryEmployeeUpdate,
            @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId =
                verifyUsers.authorizeUser(token, companyId + "/" + employeeId + "/update-salary", StringConstant.PATCH);
        salaryService.updateSalaryOfEmployee(companyId, employeeId, salaryEmployeeUpdate, userId);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

}
