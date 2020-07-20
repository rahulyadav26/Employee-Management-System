package com.assignment.application.service;

import com.assignment.application.entity.Company;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyServiceImpl implements CompanyServiceI {

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public ResponseEntity<Company> createNewCompany(Company company) {
        try {
            if(companyRepo.getCompany(company.getId())!=null
                    || companyRepo.getCompanyByName(company.getName().toUpperCase())!=null
                    || company.getId()==0){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            companyRepo.save(company);
            return new ResponseEntity<>(company,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<List<Company>> getCompanyList() {
        try {
            List<Company> companyList = companyRepo.findAll();
            return new ResponseEntity<>(companyList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<List<Object>> getCompleteCompInfo(String compName) {
        try{
            Company company = companyRepo.getCompanyByName(compName.toUpperCase());
            if(company==null){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            List<Object> objectList = companyRepo.getCompDataSet(company.getId());
            return new ResponseEntity<List<Object>>(objectList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateCompanyInfo(Long id, CompanyInfoUpdate companyInfoUpdate) {
        try{
            Company company = companyRepo.findById(id).orElse(null);
            if(company!=null){
                if(companyInfoUpdate.getFieldToUpdate().equalsIgnoreCase("Industry")){
                    company.setIndustryType(companyInfoUpdate.getUpdatedValue());
                }
                else if(companyInfoUpdate.getFieldToUpdate().equalsIgnoreCase("Employee Count")){
                    company.setEmployeeCount(Long.parseLong(companyInfoUpdate.getUpdatedValue()));
                }
                else{
                    return new ResponseEntity<>("Invalid Request",HttpStatus.OK);
                }
                companyRepo.save(company);
                return new ResponseEntity<>("Update Successful",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("No such company exists",HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteCompany(Long id) {
        try{
            Company company = companyRepo.findById(id).orElse(null);
            if(company!=null){
                companyRepo.delete(company);
                return new ResponseEntity<>("Company deleted",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("No such company exists",HttpStatus.OK);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while deleting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
