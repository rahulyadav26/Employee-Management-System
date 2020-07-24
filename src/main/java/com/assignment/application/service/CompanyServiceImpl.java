package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.exception.DuplicateCompanyException;
import com.assignment.application.exception.EmptyDatabaseException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.exception.NotExistsException;
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
    private StringConstant stringConstant;

    @Override
    public Company createNewCompany(Company company) {
        if (company == null || company.getName().isEmpty()) {
            throw new IllegalArgumentException("Data is not valid");
        }
        if (companyRepo.getCompanyByName(company.getName().toUpperCase()) != null) {
            throw new DuplicateCompanyException("Company Already Exists");
        }
        companyRepo.save(company);
        return company;
    }


    @Override
    public List<Company> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        if (companyList.size() == 0) {
            throw new EmptyDatabaseException("No Data Available");
        }
        return companyList;
    }

    @Override
    public List<CompleteCompInfo> getCompleteCompInfo(Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new RuntimeException("No such company Exists");
        }
        List<CompleteCompInfo> companyInfoList = cachingInfo.getCompanyCompleteInfo(company.getId());
        return companyInfoList;

    }

    @Override
    public String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate) {

        Company company = companyRepo.findById(id).orElse(null);
        if (company == null) {
            System.out.println("sdf");
            throw new RuntimeException("No such company exists");
        }
        if (companyInfoUpdate == null) {
            throw new EmptyUpdateException("Company Update Info not valid");
        }

        if (!companyInfoUpdate.getIndustryType().isEmpty()) {
            company.setIndustryType(companyInfoUpdate.getIndustryType());
        }
        if (!companyInfoUpdate.getEmployeeCount().isEmpty()) {
            company.setEmployeeCount(Long.parseLong(companyInfoUpdate.getEmployeeCount()));
        }
        companyRepo.save(company);
        return StringConstant.UPDATE_SUCCESSFUL;

    }

    @Override
    public String deleteCompany(Long id) {
        if (companyRepo.findById(id) != null) {
            companyRepo.deleteById(id);
            return StringConstant.DELETION_SUCCESSFUL;
        }
        throw new IllegalArgumentException("No such company Exists");

    }
}
