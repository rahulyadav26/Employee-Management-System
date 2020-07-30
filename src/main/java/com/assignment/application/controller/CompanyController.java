package com.assignment.application.controller;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
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
    private VerifyUsers verifyUsers;


    @PostMapping(value = "")
    public ResponseEntity<Company> addCompany(@RequestBody Company company,
                                              @RequestHeader("access_token") String token) {

        verifyUsers.authorizeUser(token, "company", "post");
        Company companyToBeAdded = companyServiceI.createNewCompany(company);
        return new ResponseEntity<>(companyToBeAdded, HttpStatus.OK);

    }

    @GetMapping(value = "")
    public ResponseEntity<List<Company>> getCompanyList(@RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company", "get");
        return new ResponseEntity<>(companyServiceI.getCompanyList(), HttpStatus.OK);
    }


    @GetMapping(value = "/{comp_id}/complete-info")
    public ResponseEntity<List<CompleteCompInfo>> getCompleteCompInfo(@PathVariable("comp_id") Long companyId,
                                                                      @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + companyId + "/complete-info", "get");
        List<CompleteCompInfo> objectList = companyServiceI.getCompleteCompInfo(companyId);
        return new ResponseEntity<>(objectList, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/company-update")
    public ResponseEntity<String> updateCompanyInfo(@PathVariable("id") Long id,
                                                    @RequestBody CompanyInfoUpdate companyInfoUpdate,
                                                    @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + id + "/company-update", "patch");
        companyServiceI.updateCompanyInfo(id,companyInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id,
                                                @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + id, "delete");
        companyServiceI.deleteCompany(id);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

    @PostMapping(value = "/{comp_id}/signUp")
    public ResponseEntity<String> verifyUser(@RequestHeader("username") String username) {
        companyServiceI.verifyUser(username);
        return new ResponseEntity<>(StringConstant.USER_VERIFIED, HttpStatus.OK);
    }

}
