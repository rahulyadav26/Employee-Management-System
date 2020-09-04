package com.assignment.application.util;

import com.assignment.application.dto.EmployeeDTO;
import com.assignment.application.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Base64;

@Component
public class EmployeeUtil {

    @Autowired
    private ModelMapper modelMapper;

    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        String dob = new String(Base64.getDecoder().decode(employee.getDob()));
        employeeDTO.setDob(dob);
        return employeeDTO;
    }

}
