package com.assignment.application.test.unit;


import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.service.CompanyServiceImpl;
import com.assignment.application.service.RedisService;
import com.assignment.application.update.CompanyInfoUpdate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private CachingInfo cachingInfo;

    @Mock
    private EmployeeRepo employeeRepo;



    @Test(expected = InsufficientInformationException.class)
    public void test_CompanyNull_CreateNewCompany_fails(){
        //check for conditions when company is null
        Company company = null;
        //action
        companyService.createNewCompany(company);
        //result
    }

    @Test(expected = DuplicateDataException.class)
    public void test_CompanyExist_CreateNewCompany_fails(){
        //check for name uniqueness
        Company company = new Company("Microsoft", "Technology", "California", "Bill Gates");
        when(companyRepo.getCompanyByName(company.getName().toUpperCase())).thenReturn(company);
        //action
        companyService.createNewCompany(company);
        //result
    }

    @Test(expected = InsufficientInformationException.class)
    public void test_CompanyNameIsEmpty_CreateNewCompany_fails(){
        //check for empty company name
        Company company = new Company("", "Technology", "California", "Bill Gates");
        //action
        companyService.createNewCompany(company);
        //result
    }

    @Test
    public void test_CompanyUnique_CreateNewCompany_Success(){
        //company data is valid and company doesn't exists in database
        Company company = new Company("Microsoft", "Technology","California", "Bill Gates");
        //action
        Company actualCompany = companyService.createNewCompany(company);
        //result
        Assert.assertEquals(company.getName(),actualCompany.getName());
        Assert.assertEquals(company.getFounder(),actualCompany.getFounder());
        Assert.assertEquals(company.getIndustryType(),actualCompany.getIndustryType());
        Assert.assertEquals(company.getHeadOffice(),actualCompany.getHeadOffice());
        verify(companyRepo,times(1)).getCompanyByName(company.getName().toUpperCase());
        verify(companyRepo,times(1)).save(company);
    }


    @Test
    public void test_GetCompanyList_Success(){
        //if database is not empty
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company("Microsoft", "Technology", "California", "Bill Gates"));
        companyList.add(new Company("Google", "Technology", "California", "Sergey Brin, Larry Page"));
        when(companyRepo.findAll()).thenReturn(companyList);
        //action
        List<Company> actualResult = companyService.getCompanyList();
        //result
        Assert.assertEquals(companyList.size(),actualResult.size());
        Assert.assertEquals(companyList.get(0),actualResult.get(0));
        verify(companyRepo,times(1)).findAll();
    }

    @Test(expected = RuntimeException.class)
    public void test_IdNotExist_GetCompleteCompInfo_fails(){
        //no such company exists
        final Long id = new Long(11);
        List<CompleteCompInfo> completeCompInfos = new ArrayList<>();
        when(companyRepo.findById(id)).thenReturn(null);
        //action
        companyService.getCompleteCompInfo(id);
        //result
    }

    @Test
    public void test_IdExist_GetCompleteCompInfo_Success(){
        //no such company exists
        final Long id = new Long(11);
        List<CompleteCompInfo> completeCompInfos = new ArrayList<>();
        Company company = new Company(id,"Google", "Technology","California", "Bill Gates");
        completeCompInfos.add(new CompleteCompInfo("Sundar Pichai","google_3",11L,"Engineering",2L,(double)1000000,"123454323454",null,"1234567890","California","Chennai"));
        when(companyRepo.findById(id)).thenReturn(Optional.of(company));
        when(cachingInfo.getCompanyCompleteInfo(id)).thenReturn(completeCompInfos);
        //action
        List<CompleteCompInfo> actualResult = companyService.getCompleteCompInfo(id);
        //result
        Assert.assertEquals(completeCompInfos.size(),actualResult.size());
        Assert.assertEquals(completeCompInfos.get(0),actualResult.get(0));
        verify(cachingInfo,times(1)).getCompanyCompleteInfo(id);
    }


    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_UpdateCompanyInfo_fails(){
        //company doesn't exists in db
        final Long id = new Long(11);
        when(companyRepo.findById(id)).thenReturn(null);
        CompanyInfoUpdate companyInfoUpdate = new CompanyInfoUpdate("Search Engine Platform");
        //action
        companyService.updateCompanyInfo(id,companyInfoUpdate);
    }

    @Test(expected = EmptyUpdateException.class)
    public void test_InfoEmpty_UpdateCompanyInfo_fails(){
        //company doesn't exists in db
        final Long id = new Long(11);
        Company company = new Company(id,"Google", "Technology", "California", "Bill Gates");
        when(companyRepo.findById(id)).thenReturn(Optional.of(company));
        CompanyInfoUpdate companyInfoUpdate = null;
        //action
        companyService.updateCompanyInfo(id,companyInfoUpdate);
    }

    @Test
    public void test_UpdateCompanyInfo_Success(){
        final Long id = new Long(11);
        Company company = new Company(id,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(id)).thenReturn(Optional.of(company));
        CompanyInfoUpdate companyInfoUpdate = new CompanyInfoUpdate("Search Engine Platform");
        //action
        String actualResult = companyService.updateCompanyInfo(id,companyInfoUpdate);
        //result
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
        verify(companyRepo,times(1)).save(company);
        verify(companyRepo,times(1)).findById(id);
    }

    @Test(expected = NotExistsException.class)
    public void test_CompanyNotExist_DeleteCompany_fails(){
        //no such company exists
        final Long id = new Long(11);
        Company company = new Company();
        //action
        companyService.deleteCompany(id);
        //result
    }

    @Test
    public void test_CompanyExist_DeleteCompany_success(){
        //company exists in database
        final Long id = new Long(11);
        Company company = new Company(id,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.existsById(id)).thenReturn(true);
        //action
        String actualResult = companyService.deleteCompany(id);
        //result
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,actualResult);
    }

    @Test
    public void test_verifyUser_superadmin_success(){
        //super admin verify user
        final String username = "superadmin";
        //action
        String actualResult = companyService.verifyUser(username);
        //result
        Assert.assertEquals(StringConstant.USER_VERIFIED,actualResult);
        verify(cachingInfo).tokenGenerate(anyString(),anyString());
        verify(cachingInfo).updateTokenStatus(anyString());
    }

    @Test
    public void test_verifyUser_employee_success(){
        //employee verify user
        final String username = "google_2";
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
        when(employeeRepo.getEmployee(username)).thenReturn(employee);
        //action
        String actualResult = companyService.verifyUser(username);
        //result
        Assert.assertEquals(StringConstant.USER_VERIFIED,actualResult);
        verify(cachingInfo).tokenGenerate(anyString(),anyString());
        verify(cachingInfo).updateTokenStatus(anyString());
    }

}
