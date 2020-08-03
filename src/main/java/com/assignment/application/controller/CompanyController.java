package com.assignment.application.controller;

import com.assignment.application.authenticator.VerifyUsers;
import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.CompanyDTO;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.update.CompanyInfoUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private CompanyServiceI companyServiceI;

    @Autowired
    private VerifyUsers verifyUsers;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "")
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody CompanyDTO companyDTO,
                                                 @RequestHeader("access_token") String token) {

        verifyUsers.authorizeUser(token, "company", "post");
        Company company = convertToEntity(companyDTO);
        company = companyServiceI.createNewCompany(company);
        companyDTO.setId(company.getId());
        companyDTO.setName(companyDTO.getName().toUpperCase());
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);

    }

    @GetMapping(value = "")
    public ResponseEntity<List<CompanyDTO>> getCompanyList(@RequestHeader("access_token") String token,
                                                           @RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        verifyUsers.authorizeUser(token, "company", "get");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Company> companyList = companyServiceI.getCompanyList(pageable);
        List<CompanyDTO> companyDTOList = companyList.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(companyDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/{comp_id}/complete-info")
    public ResponseEntity<List<CompleteCompInfo>> getCompleteCompInfo(@PathVariable("comp_id") Long companyId,
                                                                      @RequestHeader("access_token") String token,
                                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(defaultValue = "10")
                                                                              Integer pageSize) {
        verifyUsers.authorizeUser(token, "company/" + companyId + "/complete-info", "get");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CompleteCompInfo> objectList = companyServiceI.getCompleteCompInfo(companyId, pageable);
        return new ResponseEntity<>(objectList.toList(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/company-update")
    public ResponseEntity<String> updateCompanyInfo(@PathVariable("id") Long id,
                                                    @RequestBody CompanyInfoUpdate companyInfoUpdate,
                                                    @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + id + "/company-update", "patch");
        companyServiceI.updateCompanyInfo(id, companyInfoUpdate);
        return new ResponseEntity<>(StringConstant.UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id,
                                                @RequestHeader("access_token") String token) {
        verifyUsers.authorizeUser(token, "company/" + id, "delete");
        companyServiceI.deleteCompany(id);
        return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
    }

    //part of deploayble set 2
    @PostMapping(value = "/{comp_id}/signUp")
    public ResponseEntity<String> verifyUser(@RequestHeader("username") String username) {
        companyServiceI.verifyUser(username);
        return new ResponseEntity<>(StringConstant.USER_VERIFIED, HttpStatus.OK);
    }

    public Company convertToEntity(CompanyDTO companyDTO) {
        return modelMapper.map(companyDTO, Company.class);
    }

    public CompanyDTO convertToDTO(Company company) {
        return modelMapper.map(company, CompanyDTO.class);
    }

}
