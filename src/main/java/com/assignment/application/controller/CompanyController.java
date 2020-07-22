package com.assignment.application.controller;

import com.assignment.application.entity.Company;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "company")
public class CompanyController {

    @Autowired
    private CompanyServiceI companyServiceI;

    @Autowired
    private VerifyUser verifyUser;

    public final String updateString = "Update Successful";

    public final String deleteString = "Company Deleted";


    @PostMapping(value = "")
    public ResponseEntity<Company> addCompany(@RequestBody Company company,
                                              @RequestHeader("username") String username,
                                              @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Company companyToBeAdded = companyServiceI.createNewCompany(company);
        if (companyToBeAdded == null) {
            new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(companyToBeAdded, HttpStatus.OK);


    }

    @GetMapping(value = "")
    public ResponseEntity<List<Company>> getCompanyList(@RequestHeader("username") String username,
                                                        @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(companyServiceI.getCompanyList(), HttpStatus.OK);
    }


    @GetMapping(value = "/{comp_name}/complete-info")
    public ResponseEntity<List<Object>> getCompleteCompInfo(@PathVariable("comp_name") String compName,
                                                            @RequestHeader("username") String username,
                                                            @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        List<Object> objectList = companyServiceI.getCompleteCompInfo(compName);
        if (objectList == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(objectList, HttpStatus.OK);

    }

    @PatchMapping(value = "/{id}/company-update")
    public ResponseEntity<String> updateCompanyInfo(@PathVariable("id") Long id,
                                                    @RequestBody CompanyInfoUpdate companyInfoUpdate,
                                                    @RequestHeader("username") String username,
                                                    @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (companyServiceI.updateCompanyInfo(id, companyInfoUpdate).equals(updateString)) {
            return new ResponseEntity<>(updateString, HttpStatus.OK);
        }
        return new ResponseEntity<>("No such company exists", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (companyServiceI.deleteCompany(id).equals(deleteString)) {
            return new ResponseEntity<>(deleteString, HttpStatus.OK);
        }
        return new ResponseEntity<>("No such company exists", HttpStatus.BAD_REQUEST);
    }

}
