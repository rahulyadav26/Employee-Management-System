package com.assignment.application.util;

import com.assignment.application.dto.DepartmentDTO;
import com.assignment.application.entity.Department;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentUtil {

    @Autowired
    private ModelMapper modelMapper;

    public Department convertToEntity(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentDTO, Department.class);
    }

    public DepartmentDTO convertToDTO(Department department) {
        return modelMapper.map(department, DepartmentDTO.class);
    }

}
