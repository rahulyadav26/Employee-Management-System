package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.DepartmentDTO;
import com.assignment.application.entity.Department;
import com.assignment.application.service.interfaces.DepartmentService;
import com.assignment.application.update.DepartmentUpdate;
import com.assignment.application.util.DepartmentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DepartmentDTO> addDepartment(@PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentDTO departmentDTO,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department", "post");
        Department department = departmentUtil.convertToEntity(departmentDTO);
        department = departmentService.addDepartment(companyId, department);
        return new ResponseEntity<>(departmentUtil.convertToDTO(department), HttpStatus.OK);
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@RequestHeader("access_token") String token,
                                                              Pageable pageable) {
        verifyUser.authorizeUser(token, "department", "get");
        Page<Department> departmentList = departmentService.getDepartments(pageable);
        List<DepartmentDTO> departmentDTOS =
                departmentList.stream().map(department->departmentUtil.convertToDTO(department)).collect(Collectors.toList());
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{comp_id}/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentOfCompany(@RequestHeader("access_token") String token,
                                                                      @PathVariable("comp_id") Long companyId,
                                                                      Pageable pageable) {
        verifyUser.authorizeUser(token, "department", "get");
        Page<Department> departmentList = departmentService.getDepartmentsOfCompany(companyId, pageable);
        List<DepartmentDTO> departmentDTOS =
                departmentList.stream().map(department->departmentUtil.convertToDTO(department)).collect(Collectors.toList());
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("id") Long id,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id, "get");
        Department department = departmentService.getDepartment(companyId, id);
        return new ResponseEntity<>(departmentUtil.convertToDTO(department), HttpStatus.OK);
    }

    @PatchMapping(value = "/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") Long departmentId,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentUpdate departmentUpdate,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + departmentId + "/update-department", "patch");
        departmentService.updateDepartmentInfo(companyId, departmentId, departmentUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<String> deleteDepartmentOfCompany(@PathVariable("id") Long id,
                                                            @PathVariable("company_id") Long companyId,
                                                            @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id, "delete");
        departmentService.deleteDepartmentOfCompany(id, companyId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }


}
