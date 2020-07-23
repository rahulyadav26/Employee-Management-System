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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @Mock
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
        //assumption no such company exists
        Company company = new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        //action
        when(companyService.createNewCompany(any(Company.class))).thenReturn(company);
        //result
        Assert.assertEquals(company.getName(),"Google");
        Assert.assertEquals(company.getIndustryType(),"Technology");
        Assert.assertEquals(Optional.ofNullable(company.getEmployeeCount()),Optional.of(1000000L));
        Assert.assertEquals(company.getHeadOffice(),"California");
        Assert.assertEquals(company.getFounder(),"Sergey Brin, Larry Page");

        //assumption already existing company addition
        Company companyCopy = new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        Company companyTest = null;
        //action
        when(companyService.createNewCompany(any(Company.class))).thenReturn(companyTest);
        //result
        Assert.assertEquals(companyTest,null);

    }

    @Test
    public void testGetCompanyList() throws Exception {
        //assumption
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company("Microsoft", "Technology", 1000000L, "California", "Bill Gates"));
        companyList.add(new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page"));
        //action
        when(companyService.getCompanyList()).thenReturn(companyList);
        //result
        Assert.assertEquals(companyList.size(), 2);
        Assert.assertEquals(companyList.get(0).getName(), "Microsoft");
        Assert.assertEquals(companyList.get(0).getHeadOffice(), "California");
        Assert.assertEquals(companyList.get(0).getIndustryType(), "Technology");
        Assert.assertEquals(companyList.get(0).getFounder(), "Bill Gates");
    }

    @Test
    public void testGetCompleteCompanyInfo(){
        //assumption company exists
        List<CompleteCompInfo> completeCompInfoList = new ArrayList<>();
        completeCompInfoList.add(new CompleteCompInfo("Sundar Pichai","google_3",1L,"Engineering",2L,(double)1000000,"123454323454",null,"1234567890","California","Chennai"));
        //action
        when(companyService.getCompleteCompInfo(anyLong())).thenReturn(completeCompInfoList);
        //result
        Assert.assertEquals(completeCompInfoList.size(),1);
        Assert.assertEquals(completeCompInfoList.get(0).getEmployeeName(),"Sundar Pichai");
        Assert.assertEquals(completeCompInfoList.get(0).getEmployeeId(),"google_3");
        Assert.assertEquals(Optional.ofNullable(completeCompInfoList.get(0).getCompanyId()), Optional.of(1L));
        Assert.assertEquals(completeCompInfoList.get(0).getDepartmentName(),"Engineering");
        Assert.assertEquals(Optional.ofNullable(completeCompInfoList.get(0).getDepartmentId()),Optional.of(2L));
        Assert.assertEquals(Optional.ofNullable(completeCompInfoList.get(0).getSalary()),Optional.of(1000000.0));
        Assert.assertEquals(completeCompInfoList.get(0).getAccountNo(),"123454323454");
        Assert.assertEquals(completeCompInfoList.get(0).getProjectId(),null);
        Assert.assertEquals(completeCompInfoList.get(0).getPhoneNumber(),"1234567890");
        Assert.assertEquals(completeCompInfoList.get(0).getCurrentAdd(),"California");
        Assert.assertEquals(completeCompInfoList.get(0).getPermanentAdd(),"Chennai");

        //assumption if no such company exist
        List<CompleteCompInfo> completeCompInfosEmpty = null;
        //action
        when(companyService.getCompleteCompInfo(anyLong())).thenReturn(completeCompInfosEmpty);
        //result
        Assert.assertEquals(completeCompInfosEmpty,null);
    }

    @Test
    public void testUpdateCompanyInfo(){
        //assumption company exists and company update json is not null
        String result = stringConstants.updateStatus;
        //action
        when(companyService.updateCompanyInfo(anyLong(),any(CompanyInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.updateStatus);

        //assumption company exists and company update json is null
        result = stringConstants.invalidStatus;
        //action
        when(companyService.updateCompanyInfo(anyLong(),any(CompanyInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);

        //assumption company doesn't exists and company update json is not null
        result = stringConstants.invalidStatus;
        //action
        when(companyService.updateCompanyInfo(anyLong(),any(CompanyInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);

        //assumption company doesn't exists and company update json is null
        result = stringConstants.invalidStatus;
        //action
        when(companyService.updateCompanyInfo(anyLong(),any(CompanyInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);
    }

    @Test
    public void testDeleteCompany(){
        //assumption company to be deleted exists
        String result = stringConstants.deleteStatus;
        //action
        when(companyService.deleteCompany(anyLong())).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.deleteStatus);

        //assumption company to be deleted doesn't exists
        result = stringConstants.invalidStatus;
        //action
        when(companyService.deleteCompany(anyLong())).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);
    }

}
