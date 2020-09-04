package com.assignment.application.service.interfaces;

import com.assignment.application.entity.AccessToken;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteInfo;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface CompanyService {

    Company createNewCompany(Company company);

    Page<Company> getCompanyList(Pageable pageable);

    Page<CompleteInfo> getCompleteCompInfo(Long companyId, Pageable pageable);

    String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate, String userId);

    String deleteCompany(Long id);

    AccessToken verifyUser(String username);

}
