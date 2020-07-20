package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Company;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyServiceI {

    ResponseEntity<Company> createNewCompany(Company company);

    ResponseEntity<List<Company>> getCompanyList();

    ResponseEntity<List<Object>> getCompleteCompInfo(String compName);

    ResponseEntity<String> updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate);

    ResponseEntity<String> deleteCompany(Long id);

}
