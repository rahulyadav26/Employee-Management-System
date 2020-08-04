package com.assignment.application.util;

import com.assignment.application.dto.SalaryDTO;
import com.assignment.application.entity.Salary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalaryUtil {

    @Autowired
    private ModelMapper modelMapper;

    public Salary convertToEntity(SalaryDTO salaryDTO) {
        return modelMapper.map(salaryDTO, Salary.class);
    }

    public SalaryDTO convertToDTO(Salary salary) {
        return modelMapper.map(salary, SalaryDTO.class);
    }

}
