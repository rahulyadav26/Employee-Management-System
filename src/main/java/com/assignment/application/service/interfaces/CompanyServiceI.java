package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.update.CompanyInfoUpdate;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyServiceI {

    Company createNewCompany(Company company);

    List<Company> getCompanyList();

    List<CompleteCompInfo> getCompleteCompInfo(Long companyId);

    String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate);

    String deleteCompany(Long id);

    String verifyUser(String username);

}
