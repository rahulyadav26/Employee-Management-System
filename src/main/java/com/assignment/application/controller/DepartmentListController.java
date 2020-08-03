package com.assignment.application.controller;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.DepartmentListDTO;
import com.assignment.application.entity.DepartmentList;
import com.assignment.application.service.interfaces.DepartmentListServiceI;
import com.assignment.application.update.DepartmentInfoUpdate;
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
@RequestMapping(value = "/departmentlist")
public class DepartmentListController {

    @Autowired
    private DepartmentListServiceI departmentListServiceI;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "")
    public ResponseEntity<DepartmentListDTO> addDepartment(@RequestBody DepartmentListDTO departmentListDTO,
                                                           @RequestHeader("access_token") String token) {
        DepartmentList departmentList = convertToEntity(departmentListDTO);
        departmentList = departmentListServiceI.addDepartment(departmentList);
        departmentListDTO.setId(departmentList.getId());
        return new ResponseEntity<>(departmentListDTO, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DepartmentListDTO>> getDepartments(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<DepartmentList> departmentLists = departmentListServiceI.getDepartments(pageable);
        List<DepartmentListDTO> departmentListDTOS =
                departmentLists.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(departmentListDTOS, HttpStatus.OK);
    }

    @PatchMapping(value = "/{dept_id}")
    public ResponseEntity<String> updateDepartment(@PathVariable("dept_id") Long departmentId,
                                                   @RequestBody DepartmentInfoUpdate departmentInfoUpdate,
                                                   @RequestHeader("access_token") String token) {
        departmentListServiceI.updateDepartment(departmentId, departmentInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    public DepartmentList convertToEntity(DepartmentListDTO departmentListDTO) {
        return modelMapper.map(departmentListDTO, DepartmentList.class);
    }

    public DepartmentListDTO convertToDTO(DepartmentList departmentList) {
        return modelMapper.map(departmentList, DepartmentListDTO.class);
    }

}
