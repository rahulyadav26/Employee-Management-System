package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface CompanyServiceI {

    Company createNewCompany(Company company);

    Page<Company> getCompanyList(Pageable pageable);

    Page<CompleteCompInfo> getCompleteCompInfo(Long companyId, Pageable pageable);

    String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate);

    String deleteCompany(Long id);

    String verifyUser(String username);

}
