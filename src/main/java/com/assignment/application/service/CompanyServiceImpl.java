package com.assignment.application.service;

import com.assignment.application.Constants.StringConstants;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
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
    private StringConstants stringConstants;

    @Override
    public Company createNewCompany(Company company) {
        if (company == null
                || companyRepo.findById(company.getId()) != null
                || companyRepo.getCompanyByName(company.getName().toUpperCase()) != null
                || company.getId().equals(0)) {
            return null;
        }
        companyRepo.save(company);
        return company;
    }


    @Override
    public List<Company> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        if(companyList==null){
            return null;
        }
        return companyList;
    }

    @Override
    public List<CompleteCompInfo> getCompleteCompInfo(String compName) {
        Company company = companyRepo.getCompanyByName(compName.toUpperCase());
        if (company == null) {
            return null;
        }
        List<CompleteCompInfo> companyInfoList = cachingInfo.getCompanyCompleteInfo(company.getId());
        return companyInfoList;

    }

    @Override
    public String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate) {

        Company company = companyRepo.findById(id).orElse(null);
        if (company != null && companyInfoUpdate != null) {
            if (!companyInfoUpdate.getIndustryType().isEmpty()) {
                company.setIndustryType(companyInfoUpdate.getIndustryType());
            }
            if (!companyInfoUpdate.getEmployeeCount().isEmpty()) {
                company.setEmployeeCount(Long.parseLong(companyInfoUpdate.getEmployeeCount()));
            }
            companyRepo.save(company);
            return stringConstants.updateStatus;
        }
        return stringConstants.invalidStatus;
    }

    @Override
    public String deleteCompany(Long id) {
        Company company = companyRepo.findById(id).orElse(null);
        if (company != null) {
            companyRepo.delete(company);
            return stringConstants.deleteStatus;
        }
        return stringConstants.invalidStatus;

    }
}
