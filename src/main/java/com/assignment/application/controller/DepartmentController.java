package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.DepartmentDTO;
import com.assignment.application.entity.Department;
import com.assignment.application.service.interfaces.DepartmentService;
import com.assignment.application.update.DepartmentUpdate;
import com.assignment.application.util.DepartmentUtil;
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
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private VerifyUsers verifyUser;

    @Autowired
    private DepartmentUtil departmentUtil;

    @PostMapping(value = "/{company_id}/department")
    public ResponseEntity<DepartmentDTO> addDepartment(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                       @RequestBody @Valid DepartmentDTO departmentDTO,
                                                       @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUser.authorizeUser(token, companyId + StringConstant.DEPARTMENT, StringConstant.POST);
        Department department = departmentUtil.convertToEntity(departmentDTO);
        department = departmentService.addDepartment(companyId, department, userId);
        return new ResponseEntity<>(departmentUtil.convertToDTO(department), HttpStatus.OK);
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@RequestHeader(StringConstant.ACCESS_TOKEN) String token,
                                                              Pageable pageable) {
        verifyUser.authorizeUser(token, StringConstant.DEPARTMENT, StringConstant.GET);
        Page<Department> departmentList = departmentService.getDepartments(pageable);
        List<DepartmentDTO> departmentDTOS =
                departmentList.stream()
                              .map(department -> departmentUtil.convertToDTO(department))
                              .collect(Collectors.toList());
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{company_id}/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentOfCompany(
            @RequestHeader(StringConstant.ACCESS_TOKEN) String token,
            @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
            Pageable pageable) {
        verifyUser.authorizeUser(token, companyId + StringConstant.DEPARTMENT, StringConstant.GET);
        Page<Department> departmentList = departmentService.getDepartmentsOfCompany(companyId, pageable);
        List<DepartmentDTO> departmentDTOS =
                departmentList.stream()
                              .map(department -> departmentUtil.convertToDTO(department))
                              .collect(Collectors.toList());
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("id") @NonNull Long id,
                                                       @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                       @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        verifyUser.authorizeUser(token, companyId + StringConstant.DEPARTMENT + "/" + id, StringConstant.GET);
        Department department = departmentService.getDepartment(companyId, id);
        return new ResponseEntity<>(departmentUtil.convertToDTO(department), HttpStatus.OK);
    }

    @PatchMapping(value = "/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") @NonNull Long departmentId,
                                                       @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
                                                       @RequestBody @Valid DepartmentUpdate departmentUpdate,
                                                       @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUser.authorizeUser(token,
                                                 companyId + StringConstant.DEPARTMENT + "/" + departmentId +
                                                 "/update-department",
                                                 StringConstant.PATCH);
        departmentService.updateDepartmentInfo(companyId, departmentId, departmentUpdate, userId);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<String> deleteDepartmentOfCompany(@PathVariable("id") @NonNull Long id,
                                                            @PathVariable(StringConstant.COMPANY_ID)
                                                            @NonNull Long companyId,
                                                            @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUser.authorizeUser(token, companyId + StringConstant.DEPARTMENT + "/" + id,
                                                 StringConstant.DELETE);
        departmentService.deleteDepartmentOfCompany(id, companyId, userId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

}
