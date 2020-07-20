package com.assignment.application.controller;

import com.assignment.application.entity.Company;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "company")
public class CompanyController {

    @Autowired
    private CompanyServiceI companyServiceI;


    @PostMapping(value = "")
    public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return companyServiceI.createNewCompany(company);
    }

    @GetMapping(value="")
    public ResponseEntity<List<Company>> getCompanyList(){
        return companyServiceI.getCompanyList();
    }


    @GetMapping(value="/{comp_name}/complete-info")
    public ResponseEntity<List<Object>> getCompleteCompInfo(@PathVariable("comp_name")String compName){
        return companyServiceI.getCompleteCompInfo(compName);
    }

    @PatchMapping(value="/{id}/company-update")
    public ResponseEntity<String> updateCompanyInfo(@PathVariable("id") Long id,@RequestBody CompanyInfoUpdate companyInfoUpdate){
        return companyServiceI.updateCompanyInfo(id,companyInfoUpdate);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id){
        return companyServiceI.deleteCompany(id);
    }

}
