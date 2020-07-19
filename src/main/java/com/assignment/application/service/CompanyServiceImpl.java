package com.assignment.application.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.service.interfaces.CompanyServiceInterface;
import com.assignment.application.update.EmployeeUpdate;
import com.assignment.application.update.IndustryUpdate;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyServiceImpl implements CompanyServiceInterface {

    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private Company companyNew;

    @Override
    public ResponseEntity<Company> createNewCompany(Company company) {
        try {
            if(companyRepo.getCompany(company.getId())!=null || companyRepo.getCompanyByName(company.getName().toUpperCase())!=null){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            companyNew.setId(company.getId());
            companyNew.setName(company.getName());
            companyNew.setIndustry_type(company.getIndustry_type());
            companyNew.setEmployee_count(company.getEmployee_count());
            companyNew.setFounder(company.getFounder());
            companyNew.setHead_office(company.getHead_office());
            companyRepo.save(companyNew);
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
            companyNew = companyRepo.getCompanyByName(compName.toUpperCase());
            if(companyNew==null){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            List<Object> objectList = companyRepo.getCompDataSet(companyNew.getId());
            return new ResponseEntity<>(objectList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateIndustryType(long id,IndustryUpdate industryUpdate) {
        try{
            companyNew = companyRepo.findById(id).orElse(null);
            if(companyNew!=null){
                companyNew.setIndustry_type(industryUpdate.getIndustryType());
                companyRepo.save(companyNew);
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
    public ResponseEntity<String> updateEmployeeCount(long id,EmployeeUpdate employeeUpdate) {
        try{
            companyNew = companyRepo.findById(id).orElse(null);
            if(companyNew!=null){
                companyNew.setEmployee_count(employeeUpdate.getEmployeeCount());
                companyRepo.save(companyNew);
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
    public ResponseEntity<String> deleteCompany(long id) {
        try{
            companyNew = companyRepo.findById(id).orElse(null);
            if(companyNew!=null){
                companyRepo.delete(companyNew);
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
