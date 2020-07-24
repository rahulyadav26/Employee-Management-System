package com.assignment.application.test;


import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.exception.DuplicateCompanyException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.service.CompanyServiceImpl;
import com.assignment.application.update.CompanyInfoUpdate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    public static final String GOOGLE = "GOOGLE";
    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepo companyRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalidCompany_createNewCompany_failsWithIAE() {
        //TODO rewrite with Rule for expected exception , it allows you to check for message string also
        // assumptions or when conditions
        verifyNoInteractions(companyRepo);

        //execute
        companyService.createNewCompany(null);

        //validate results
    }

    @Test(expected = DuplicateCompanyException.class)
    public void test_companyExist_createNewCompany_fails() {
        // check against name uniqueness
        Company company = new Company(GOOGLE, "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        when(companyRepo.getCompanyByName(GOOGLE)).thenReturn(company);

        verify(companyRepo).getCompanyByName(GOOGLE);
        companyService.createNewCompany(company);
    }


    @Test
    public void testCreateNewCompany() {
        //assumption
        Company company = new Company("Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        //action
        when(companyRepo.save(any(Company.class))).thenReturn(company);
        //result
        Company actualResult = companyService.createNewCompany(company);
        Assert.assertEquals(company.getFounder(), actualResult.getFounder());
        Assert.assertEquals(company.getHeadOffice(), actualResult.getHeadOffice());
        Assert.assertEquals(company.getEmployeeCount(), actualResult.getEmployeeCount());
        Assert.assertEquals(company.getIndustryType(), actualResult.getIndustryType());
        Assert.assertEquals(company.getName(), actualResult.getName());
        Assert.assertEquals(company.getId(), actualResult.getId());

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
        Assert.assertEquals(2, actualResult.size());
        Assert.assertEquals(companyList.get(0).getName(), actualResult.get(0).getName());
        Assert.assertEquals(companyList.get(0).getFounder(), actualResult.get(0).getFounder());
        Assert.assertEquals(companyList.get(0).getIndustryType(), actualResult.get(0).getIndustryType());
        Assert.assertEquals(companyList.get(0).getEmployeeCount(), actualResult.get(0).getEmployeeCount());
    }

    @Test
    public void testGetCompleteCompanyInfo() {
        //assumption
        List<CompleteCompInfo> completeCompInfoList = new ArrayList<>();
        completeCompInfoList.add(new CompleteCompInfo("Sundar Pichai", "google_3", 1L, "Engineering", 2L, (double) 1000000, "123454323454", null, "1234567890", "California", "Chennai"));
        //action
        when(companyRepo.getCompanyCompleteInfo(anyLong())).thenReturn(completeCompInfoList);
        //result
        List<CompleteCompInfo> actualResult = companyService.getCompleteCompInfo(1L);
        Assert.assertEquals(null, actualResult);
    }

    @Test
    public void testUpdateCompanyInfo() {
        //assumption
        Company company = new Company(9L, "Google", "Technology", 1000000L, "California", "Sergey Brin, Larry Page");
        CompanyInfoUpdate companyInfoUpdate = new CompanyInfoUpdate("Search Engine Platform", "");
        String expectedResult = StringConstant.INVALID_CREDENTIALS;
        //action
        if (!companyInfoUpdate.getEmployeeCount().isEmpty()) {
            company.setEmployeeCount(Long.parseLong(companyInfoUpdate.getEmployeeCount()));
        }
        if (companyInfoUpdate.getIndustryType().isEmpty()) {
            company.setIndustryType(companyInfoUpdate.getIndustryType());
        }
        when(companyRepo.findById(9L)).thenReturn(Optional.of(company));
        when(companyRepo.save(any(Company.class))).thenReturn(company);
        //result
        String actualResult = companyService.updateCompanyInfo(company.getId(), companyInfoUpdate);

        Assert.assertEquals(expectedResult, actualResult);
    }

}
