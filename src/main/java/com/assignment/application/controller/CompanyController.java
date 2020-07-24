package com.assignment.application.controller;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
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

    @Autowired
    private StringConstant stringConstant;

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


    @GetMapping(value = "/{comp_id}/complete-info")
    public ResponseEntity<List<CompleteCompInfo>> getCompleteCompInfo(@PathVariable("comp_id") Long companyId,
                                                            @RequestHeader("username") String username,
                                                            @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        List<CompleteCompInfo> objectList = companyServiceI.getCompleteCompInfo(companyId);
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
        if (companyServiceI.updateCompanyInfo(id, companyInfoUpdate).equals(stringConstant.updateStatus)) {
            return new ResponseEntity<>(stringConstant.updateStatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(stringConstant.deleteStatus, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password) {
        int status = verifyUser.authorizeUser(username, password);
        if (status == 0) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (companyServiceI.deleteCompany(id).equals(stringConstant.deleteStatus)) {
            return new ResponseEntity<>(stringConstant.deleteStatus, HttpStatus.OK);
        }
        return new ResponseEntity<>(stringConstant.invalidStatus, HttpStatus.BAD_REQUEST);
    }

}
