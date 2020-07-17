package com.assignment.application.service.interfaces;

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

    ResponseEntity<String> updateIndustryType(long id,IndustryUpdate industryUpdate);

    ResponseEntity<String> updateEmployeeCount(long id,EmployeeUpdate employeeUpdate);

    ResponseEntity<String> deleteCompany(long id);

}
