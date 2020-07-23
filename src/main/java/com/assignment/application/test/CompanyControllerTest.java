package com.assignment.application.test;


import com.assignment.application.Constants.StringConstants;
import com.assignment.application.controller.CompanyController;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.service.CompanyServiceImpl;
import com.assignment.application.service.interfaces.CompanyServiceI;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private CachingInfo cachingInfo;

    @Mock
    private StringConstants stringConstants;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCompanyList() throws Exception {
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company("Microsoft", "Technology", 1000000L, "California", "Bill Gates"));
        companyList.add(new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page"));
        when(companyService.getCompanyList()).thenReturn(companyList);
        Assert.assertEquals(companyList.size(), 2);
        Assert.assertEquals(companyList.get(0).getName(), "Microsoft");
        Assert.assertEquals(companyList.get(0).getHeadOffice(), "California");
        Assert.assertEquals(companyList.get(0).getIndustryType(), "Technology");
        Assert.assertEquals(companyList.get(0).getFounder(), "Bill Gates");
    }

}
