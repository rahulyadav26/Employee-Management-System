package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyServiceI {

    Company createNewCompany(Company company);

    List<Company> getCompanyList();

    List<CompleteCompInfo> getCompleteCompInfo(String compName);

    String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate);

    String deleteCompany(Long id);

}
