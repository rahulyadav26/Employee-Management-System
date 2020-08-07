package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.CompanyDTO;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteInfo;
import com.assignment.application.service.interfaces.CompanyService;
import com.assignment.application.update.CompanyInfoUpdate;
import com.assignment.application.util.CompanyUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody @Valid CompanyDTO companyDTO,
                                                 @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {

        verifyUsers.authorizeUser(token, "/" + StringConstant.COMPANY, StringConstant.POST);
        Company company = companyUtil.convertToEntity(companyDTO);
        company = companyService.createNewCompany(company);
        return new ResponseEntity<>(companyUtil.convertToDTO(company), HttpStatus.OK);

    }

    @GetMapping(value = "")
    public ResponseEntity<List<CompanyDTO>> getCompanyList(
            @RequestHeader(StringConstant.ACCESS_TOKEN) @Valid String token,
            Pageable pageable) {
        verifyUsers.authorizeUser(token, "/" + StringConstant.COMPANY, StringConstant.GET);
        Page<Company> companyList = companyService.getCompanyList(pageable);
        List<CompanyDTO> companyDTOList = companyList.stream()
                                                     .map(companyDTO -> companyUtil.convertToDTO(companyDTO))
                                                     .collect(Collectors.toList());
        return new ResponseEntity<>(companyDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/{company_id}/complete-info")
    public ResponseEntity<List<CompleteInfo>> getCompleteCompInfo(
            @PathVariable(StringConstant.COMPANY_ID) @NonNull Long companyId,
            @RequestHeader(StringConstant.ACCESS_TOKEN) @Valid String token,
            Pageable pageable) {
        verifyUsers.authorizeUser(token, StringConstant.COMPANY + "/" + companyId + "/complete-info",
                                  StringConstant.GET);
        Page<CompleteInfo> objectList = companyService.getCompleteCompInfo(companyId, pageable);
        return new ResponseEntity<>(objectList.toList(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{company_id}/company-update")
    public ResponseEntity<String> updateCompanyInfo(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long id,
                                                    @RequestBody @Valid CompanyInfoUpdate companyInfoUpdate,
                                                    @RequestHeader(StringConstant.ACCESS_TOKEN) String token) {
        String userId = verifyUsers.authorizeUser(token, StringConstant.COMPANY + "/" + id + "/company-update",
                                                  StringConstant.PATCH);
        companyService.updateCompanyInfo(id, companyInfoUpdate, userId);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{company_id}")
    public ResponseEntity<String> deleteCompany(@PathVariable(StringConstant.COMPANY_ID) @NonNull Long id,
                                                @RequestHeader(StringConstant.ACCESS_TOKEN) @Valid String token) {
        verifyUsers.authorizeUser(token, StringConstant.COMPANY + "/" + id, StringConstant.DELETE);
        companyService.deleteCompany(id);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

    @PostMapping(value = "/{company_id}/signUp")
    public ResponseEntity<String> verifyUser(@RequestHeader("username") String username) {
        return new ResponseEntity<>("Your access token is " + companyService.verifyUser(username), HttpStatus.OK);
    }

}
