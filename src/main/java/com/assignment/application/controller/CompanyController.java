package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.CompanyDTO;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteInfo;
import com.assignment.application.service.interfaces.CompanyService;
import com.assignment.application.update.CompanyInfoUpdate;
import com.assignment.application.util.CompanyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private VerifyUsers verifyUsers;

    @Autowired
    private CompanyUtil companyUtil;

    @PostMapping(value = "")
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody CompanyDTO companyDTO,
                                                 @RequestHeader("access_token") String token) {

        verifyUsers.authorizeUser(token, "company", "post");
        Company company = companyUtil.convertToEntity(companyDTO);
        company = companyService.createNewCompany(company);
        return new ResponseEntity<>(companyUtil.convertToDTO(company), HttpStatus.OK);

    }

    @GetMapping(value = "")
    public ResponseEntity<List<CompanyDTO>> getCompanyList(@RequestHeader("access_token") String token,
                                                           Pageable pageable) {
        verifyUsers.authorizeUser(token, "company", "get");
        Page<Company> companyList = companyService.getCompanyList(pageable);
        List<CompanyDTO> companyDTOList = companyList.stream().map(companyDTO->companyUtil.convertToDTO(companyDTO)).collect(Collectors.toList());
        return new ResponseEntity<>(companyDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/{comp_id}/complete-info")
    public ResponseEntity<List<CompleteInfo>> getCompleteCompInfo(@PathVariable("comp_id") Long companyId,
                                                                  @RequestHeader("access_token") String token,
                                                                  Pageable pageable) {
        verifyUsers.authorizeUser(token, "company/" + companyId + "/complete-info", "get");
        Page<CompleteInfo> objectList = companyService.getCompleteCompInfo(companyId, pageable);
        return new ResponseEntity<>(objectList.toList(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/company-update")
    public ResponseEntity<String> updateCompanyInfo(@PathVariable("id") Long id,
                                                    @RequestBody CompanyInfoUpdate companyInfoUpdate,
                                                    @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + id + "/company-update", "patch");
        companyService.updateCompanyInfo(id, companyInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id,
                                                @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + id, "delete");
        companyService.deleteCompany(id);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

    //part of deploayble set 2
    @PostMapping(value = "/{comp_id}/signUp")
    public ResponseEntity<String> verifyUser(@RequestHeader("username") String username) {
        companyService.verifyUser(username);
        return new ResponseEntity<>(StringConstant.USER_VERIFIED, HttpStatus.OK);
    }

}
