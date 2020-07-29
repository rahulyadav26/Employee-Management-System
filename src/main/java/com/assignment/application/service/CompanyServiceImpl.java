package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
        if (company == null || company.getName().isEmpty()) {
            throw new InsufficientInformationException("Either request body is null or no company name found in request");
        }
        if (companyRepo.getCompanyByName(company.getName().toUpperCase()) != null) {
            throw new DuplicateDataException("Company Already Exists");
        }
        companyRepo.save(company);
        return company;
    }


    @Override
    public List<Company> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        return companyList;
    }

    @Override
    public List<CompleteCompInfo> getCompleteCompInfo(Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        List<CompleteCompInfo> companyInfoList = cachingInfo.getCompanyCompleteInfo(company.getId());
        return companyInfoList;

    }

    @Override
    public String updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate) {

        Company company = companyRepo.findById(id).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        if (companyInfoUpdate == null) {
            throw new EmptyUpdateException("Company Update Info not valid");
        }

        if (!companyInfoUpdate.getIndustryType().isEmpty()) {
            company.setIndustryType(companyInfoUpdate.getIndustryType());
        }
        companyRepo.save(company);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteCompany(Long id) {
        if (companyRepo.existsById(id)==true) {
            companyRepo.deleteById(id);
            return StringConstant.DELETION_SUCCESSFUL;
        }
        throw new NotExistsException("No such company exists");
    }

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
        String accessToken = employeeId[1] + "-" + UUID.randomUUID().toString() + "-" + employee.getCompanyId();
        cachingInfo.tokenGenerate(accessToken, username);
        cachingInfo.updateTokenStatus(username);
        return StringConstant.USER_VERIFIED;
    }
}
