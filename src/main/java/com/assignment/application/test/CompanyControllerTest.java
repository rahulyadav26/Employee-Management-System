package com.assignment.application.test;


import com.assignment.application.Constants.StringConstants;
import com.assignment.application.controller.CompanyController;
import com.assignment.application.entity.Company;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.service.CompanyServiceImpl;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyServiceI companyServiceI;

    @Mock
    private StringConstants stringConstants;

    @Mock
    private VerifyUser verifyUser;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private CachingInfo cachingInfo;

    @Test
    public void testGetCompanyList() throws Exception{
        Company company1 = new Company("Microsoft","Technology",(long)1000000,"California","Bill Gates");
        List<Company> list = new ArrayList<>();
        when(companyServiceI.getCompanyList()).thenReturn(list);
        ResponseEntity<List<Company>> companyList = companyController.getCompanyList("admin","admin");
        Assert.assertEquals(companyList.getStatusCode().value(),200);
        Assert.assertEquals(companyList.getBody().size(),6);
    }

}
