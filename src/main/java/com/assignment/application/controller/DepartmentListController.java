package com.assignment.application.controller;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.DepartmentListDTO;
import com.assignment.application.entity.DepartmentList;
import com.assignment.application.service.interfaces.DepartmentListService;
import com.assignment.application.update.DepartmentInfoUpdate;
import com.assignment.application.util.DepartmentListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/department-list")
public class DepartmentListController {

    @Autowired
    private DepartmentListService departmentListService;

    @Autowired
    private DepartmentListUtil departmentListUtil;

    @PostMapping(value = "")
    public ResponseEntity<DepartmentListDTO> addDepartment(@RequestBody DepartmentListDTO departmentListDTO,
                                                           @RequestHeader("access_token") String token) {
        DepartmentList departmentList = departmentListUtil.convertToEntity(departmentListDTO);
        departmentList = departmentListService.addDepartment(departmentList);
        return new ResponseEntity<>(departmentListUtil.convertToDTO(departmentList), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DepartmentListDTO>> getDepartments(Pageable pageable) {
        Page<DepartmentList> departmentLists = departmentListService.getDepartments(pageable);
        List<DepartmentListDTO> departmentListDTOS =
                departmentLists.stream()
                               .map(departmentList -> departmentListUtil.convertToDTO(departmentList))
                               .collect(Collectors.toList());
        return new ResponseEntity<>(departmentListDTOS, HttpStatus.OK);
    }

    @PatchMapping(value = "/{dept_id}")
    public ResponseEntity<String> updateDepartment(@PathVariable("dept_id") Long departmentId,
                                                   @RequestBody DepartmentInfoUpdate departmentInfoUpdate,
                                                   @RequestHeader("access_token") String token) {
        departmentListService.updateDepartment(departmentId, departmentInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

}
