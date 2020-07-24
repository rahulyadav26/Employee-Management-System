package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.exception.DuplicateCompanyException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyServiceImpl implements CompanyServiceI {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private StringConstant stringConstants;

    @Override
    public Company createNewCompany(Company company) {
        if (company == null || company.getName() == null || company.getName().isEmpty()) {
            throw new IllegalArgumentException("Company details not found or company name is empty");
        }

        if (companyRepo.getCompanyByName(company.getName().toUpperCase()) != null) {
            throw new DuplicateCompanyException("Company already exists with this name");
        }
        companyRepo.save(company);
        return company;
    }


    @Override
    public List<Company> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        if (companyList == null) {
            return null;
        }
        return companyList;
    }

    @Override
    public List<CompleteCompInfo> getCompleteCompInfo(Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            return null;
        }
        List<CompleteCompInfo> companyInfoList = cachingInfo.getCompanyCompleteInfo(company.getId());
        return companyInfoList;

    }

    @Override
    public String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate) {

        Company company = companyRepo.findById(id).orElse(null);
        if(company == null ){
            // throw IAE
        }
        if (company != null && companyInfoUpdate != null) {
            if (!companyInfoUpdate.getIndustryType().isEmpty()) {
                company.setIndustryType(companyInfoUpdate.getIndustryType());
            }
            if (!companyInfoUpdate.getEmployeeCount().isEmpty()) {
                company.setEmployeeCount(Long.parseLong(companyInfoUpdate.getEmployeeCount()));
            }
            companyRepo.save(company);
            return StringConstant.UPDATE_SUCCESSFUL;
        }
        return StringConstant.FAILED;
    }

    @Override
    public String deleteCompany(Long id) {
        Company company = companyRepo.findById(id).orElse(null);
        if (company != null) {
            companyRepo.delete(company);
            return stringConstants.DELETION_SUCCESSFUL;
        }
        return stringConstants.FAILED;

    }
}
