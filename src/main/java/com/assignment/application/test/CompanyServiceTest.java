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
import com.assignment.application.update.CompanyInfoUpdate;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class CompanyServiceTest {

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
    public void testCreateNewCompany(){
        //assumption
        Company company = new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        //action
        when(companyRepo.save(any(Company.class))).thenReturn(company);
        //result
        Company actualResult = companyService.createNewCompany(company);
        Assert.assertEquals(company.getFounder(),actualResult.getFounder());
        Assert.assertEquals(company.getHeadOffice(),actualResult.getHeadOffice());
        Assert.assertEquals(company.getEmployeeCount(),actualResult.getEmployeeCount());
        Assert.assertEquals(company.getIndustryType(),actualResult.getIndustryType());
        Assert.assertEquals(company.getName(),actualResult.getName());
        Assert.assertEquals(company.getId(),actualResult.getId());

    }

    @Test
    public void testGetCompanyList() throws Exception {
        //assumption
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company("Microsoft", "Technology", 1000000L, "California", "Bill Gates"));
        companyList.add(new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page"));
        //action
        when(companyRepo.findAll()).thenReturn(companyList);
        //result
        List<Company> actualResult = companyRepo.findAll();
        Assert.assertEquals(2,actualResult.size());
        Assert.assertEquals(companyList.get(0).getName(),actualResult.get(0).getName());
        Assert.assertEquals(companyList.get(0).getFounder(),actualResult.get(0).getFounder());
        Assert.assertEquals(companyList.get(0).getIndustryType(),actualResult.get(0).getIndustryType());
        Assert.assertEquals(companyList.get(0).getEmployeeCount(),actualResult.get(0).getEmployeeCount());
    }

    @Test
    public void testGetCompleteCompanyInfo(){
        //assumption
        List<CompleteCompInfo> completeCompInfoList = new ArrayList<>();
        completeCompInfoList.add(new CompleteCompInfo("Sundar Pichai","google_3",1L,"Engineering",2L,(double)1000000,"123454323454",null,"1234567890","California","Chennai"));
        //action
        when(companyRepo.getCompanyCompleteInfo(anyLong())).thenReturn(completeCompInfoList);
        //result
        List<CompleteCompInfo> actualResult = companyService.getCompleteCompInfo(1L);
        Assert.assertEquals(null,actualResult);
    }

    @Test
    public void testUpdateCompanyInfo(){
        //assumption
        Company company = new Company(9L,"Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        CompanyInfoUpdate companyInfoUpdate = new CompanyInfoUpdate("Search Engine Platform","");
        String expectedResult = stringConstants.invalidStatus;
        //action
        if(!companyInfoUpdate.getEmployeeCount().isEmpty()){
            company.setEmployeeCount(Long.parseLong(companyInfoUpdate.getEmployeeCount()));
        }
        if(companyInfoUpdate.getIndustryType().isEmpty()){
            company.setIndustryType(companyInfoUpdate.getIndustryType());
        }
        when(companyRepo.save(any(Company.class))).thenReturn(company);
        //result
        String actualResult = companyService.updateCompanyInfo(company.getId(),companyInfoUpdate);
        Assert.assertEquals(expectedResult,actualResult);
    }

}
