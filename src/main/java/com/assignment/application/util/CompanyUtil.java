package com.assignment.application.util;

import com.assignment.application.dto.CompanyDTO;
import com.assignment.application.entity.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyUtil {

    @Autowired
    private ModelMapper modelMapper;

    public Company convertToEntity(CompanyDTO companyDTO) {
        return modelMapper.map(companyDTO, Company.class);
    }

    public CompanyDTO convertToDTO(Company company) {
        return modelMapper.map(company, CompanyDTO.class);
    }

}
