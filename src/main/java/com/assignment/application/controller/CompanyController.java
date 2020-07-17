package com.assignment.application.controller;

import com.assignment.application.entity.Company;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.service.interfaces.CompanyServiceInterface;
import com.assignment.application.update.EmployeeUpdate;
import com.assignment.application.update.IndustryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "company")
public class CompanyController {

    @Autowired
    private CompanyServiceInterface companyServiceInterface;


    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return companyServiceInterface.createNewCompany(company);
    }

    @RequestMapping(value="" , method = RequestMethod.GET)
    public ResponseEntity<List<Company>> getCompanyList(){
        return companyServiceInterface.getCompanyList();
    }

    @RequestMapping(value="/{id}/industry-type" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateIndustryType(@PathVariable("id") long id,@RequestBody IndustryUpdate industryUpdate){
        return companyServiceInterface.updateIndustryType(id,industryUpdate);
    }

    @RequestMapping(value="/{id}/employee-count" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateEmployeeCount(@PathVariable("id") long id,@RequestBody EmployeeUpdate employeeUpdate){
        return companyServiceInterface.updateEmployeeCount(id,employeeUpdate);
    }

    @RequestMapping(value="/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id){
        return companyServiceInterface.deleteCompany(id);
    }

}
