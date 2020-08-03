package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.DepartmentDTO;
import com.assignment.application.entity.Department;
import com.assignment.application.service.interfaces.DepartmentServiceI;
import com.assignment.application.update.DepartmentUpdate;
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
public class DepartmentController {

    @Autowired
    private DepartmentServiceI departmentServiceI;

    @Autowired
    private VerifyUsers verifyUser;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{company_id}/department")
    public ResponseEntity<DepartmentDTO> addDepartment(@PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentDTO departmentDTO,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department", "post");
        Department department = convertToEntity(departmentDTO);
        department = departmentServiceI.addDepartment(companyId, department);
        departmentDTO.setId(department.getId());
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@RequestHeader("access_token") String token,
                                                              @RequestParam(defaultValue = "0") Integer pageNo,
                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        verifyUser.authorizeUser(token, "department", "get");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Department> departmentList = departmentServiceI.getDepartments(pageable);
        List<DepartmentDTO> departmentDTOS =
                departmentList.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{comp_id}/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentOfCompany(@RequestHeader("access_token") String token,
                                                                      @PathVariable("comp_id") Long companyId,
                                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(defaultValue = "10")
                                                                              Integer pageSize) {
        verifyUser.authorizeUser(token, "department", "get");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Department> departmentList = departmentServiceI.getDepartmentsOfCompany(companyId, pageable);
        List<DepartmentDTO> departmentDTOS =
                departmentList.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("id") Long id,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id, "get");
        Department department = departmentServiceI.getDepartment(companyId, id);
        return new ResponseEntity<>(convertToDTO(department), HttpStatus.OK);
    }

    @PatchMapping(value = "/{company_id}/department/{id}/update-department")
    public ResponseEntity<String> updateDepartmentInfo(@PathVariable("id") Long departmentId,
                                                       @PathVariable("company_id") Long companyId,
                                                       @RequestBody DepartmentUpdate departmentUpdate,
                                                       @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + departmentId + "/update-department", "patch");
        departmentServiceI.updateDepartmentInfo(companyId, departmentId, departmentUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{company_id}/department/{id}")
    public ResponseEntity<String> deleteDepartmentOfCompany(@PathVariable("id") Long id,
                                                            @PathVariable("company_id") Long companyId,
                                                            @RequestHeader("access_token") String token) {
        verifyUser.authorizeUser(token, companyId + "/department/" + id, "delete");
        departmentServiceI.deleteDepartmentOfCompany(id, companyId);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

    public Department convertToEntity(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentDTO, Department.class);
    }

    public DepartmentDTO convertToDTO(Department department) {
        return modelMapper.map(department, DepartmentDTO.class);
    }

}
