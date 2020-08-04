package com.assignment.application.util;

import com.assignment.application.dto.DepartmentListDTO;
import com.assignment.application.entity.DepartmentList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentListUtil {

    @Autowired
    private ModelMapper modelMapper;

    public DepartmentList convertToEntity(DepartmentListDTO departmentListDTO) {
        return modelMapper.map(departmentListDTO, DepartmentList.class);
    }

    public DepartmentListDTO convertToDTO(DepartmentList departmentList) {
        return modelMapper.map(departmentList, DepartmentListDTO.class);
    }

}
