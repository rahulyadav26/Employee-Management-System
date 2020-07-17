package com.assignment.application.repo;

import com.assignment.application.entity.Company;
import com.assignment.application.update.EmployeeUpdate;
import com.assignment.application.update.IndustryUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyServiceInterface {

    ResponseEntity<Company> createNewCompany(Company company);

    ResponseEntity<List<Company>> getCompanyList();

    ResponseEntity<String> updateIndustryType(IndustryUpdate industryUpdate);

    ResponseEntity<String> updateEmployeeCount(EmployeeUpdate employeeUpdate);

    ResponseEntity<String> deleteCompany(long id);

}
