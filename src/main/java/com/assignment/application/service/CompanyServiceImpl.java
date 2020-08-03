package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.DuplicateDataException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class CompanyServiceImpl implements CompanyServiceI {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private StringConstant stringConstant;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public Company createNewCompany(Company company) {
        if (company == null || company.getName() == null || company.getName().isEmpty()) {
            throw new InsufficientInformationException(
                    "Either request body is null or no company name found in request");
        }
        Company checkCompany = companyRepo.getCompanyByName(company.getName().toUpperCase());
        if (checkCompany != null && checkCompany.getIsActive() == 1) {
            throw new DuplicateDataException("Company Already Exists");
        }
        company.setName(company.getName().toUpperCase());
        if (checkCompany != null) {
            checkCompany.setIsActive(1L);
            checkCompany.setUpdatedBy("0");
            checkCompany.setUpdatedAt(new Date());
            companyRepo.save(checkCompany);
            return checkCompany;
        }
        companyRepo.save(company);
        return company;
    }

    @Override
    public Page<Company> getCompanyList(Pageable pageable) {
        return companyRepo.findAll(pageable);
    }

    //to do
    @Override
    public Page<CompleteCompInfo> getCompleteCompInfo(Long companyId, Pageable pageable) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        return cachingInfo.getCompanyCompleteInfo(company.getId(), pageable);

    }

    @Override
    public String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate) {

        Company company = companyRepo.findById(id).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (companyInfoUpdate == null || companyInfoUpdate.getIndustryType() == null ||
            companyInfoUpdate.getIndustryType().isEmpty()) {
            throw new EmptyUpdateException("Company Update Info not valid");
        }
        company.setIndustryType(companyInfoUpdate.getIndustryType());
        Date date = new Date();
        company.setUpdatedAt(date);
        company.setUpdatedBy("0");
        companyRepo.save(company);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    //@CacheEvict(value = "companyCompleteInfo", key = "#companyId")
    public String deleteCompany(Long id) {
        Company company = companyRepo.findById(id).orElse(null);
        if (company != null && company.getIsActive() == 1) {
            Date date = new Date();
            company.setIsActive(0L);
            company.setUpdatedAt(date);
            company.setUpdatedBy("0");
            companyRepo.save(company);
            return StringConstant.DELETION_SUCCESSFUL;
        }
        throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
    }

    //part of deployable set 2
    @Override
    public String verifyUser(String username) {
        if (username.equalsIgnoreCase("superadmin")) {
            String accessToken = UUID.randomUUID().toString();
            cachingInfo.tokenGenerate(accessToken, username);
            cachingInfo.updateTokenStatus(username);
            return StringConstant.USER_VERIFIED;
        }
        Employee employee = employeeRepo.getEmployee(username);
        String[] employeeId = employee.getEmployeeId().split("_");
        //String accessToken = employeeId[1] + "-" + UUID.randomUUID().toString() + "-" + employee.getCompanyId();
        //  cachingInfo.tokenGenerate(accessToken, username);
        cachingInfo.updateTokenStatus(username);
        return StringConstant.USER_VERIFIED;
    }
}
