package com.assignment.application.test.unit;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.authenticator.VerifyUser;
import com.assignment.application.entity.Company;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VerifyUserTest {

    @InjectMocks
    private VerifyUser verifyUser;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private RedisService redisService;

    @Test
    public void test_Superadmin_AuthorizeUser_success(){
        //authorize super admin
        String token = "f2345650-ec3b-4d2e-96fd-6e34dca77df7";
        String cachedInfo = "roles: superadmin";
        when(redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token)).thenReturn(cachedInfo);
        //action
        int actualResult = verifyUser.authorizeUser(token,"/company","post");
        //result
        Assert.assertEquals(1,actualResult);
        verify(redisService).getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token);
    }

    @Test
    public void test_employeeUrlNotAuthorized_AuthorizeUser_fails(){
        //url employee can't access
        String token = "2-f2345650-ec3b-4d2e-96fd-6e34dca77df7-9";
        String cachedInfo = "Employee{id=15, name='Larry Page', dob='5/10/1989', permanentAdd='Chennai', currentAdd='California', phoneNumber='1234567890', position='CEO', departmentId=132, projectId=null, companyId=9, employeeId='google_2'}roles: employee";
        Company company = new Company(9L,"Google", "Technology", "California", "Sergey Brin, Larry Page");
        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
        when(redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token)).thenReturn(cachedInfo);
        String url = "/company/9/complete-info";
        //action
        int actualResult = verifyUser.authorizeUser(token,url,"post");
        //result
        Assert.assertEquals(0,actualResult);
        verify(redisService).getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token);
        verify(companyRepo).findById(anyLong());
    }

    @Test
    public void test_EmployeeIdNotMatcheswithToken_AuthorizeUser_fails(){
        //employee Id not matches with token
        String token = "9-f2345650-ec3b-4d2e-96fd-6e34dca77df7-9";
        String cachedInfo = "Employee{id=15, name='Larry Page', dob='5/10/1989', permanentAdd='Chennai', currentAdd='California', phoneNumber='1234567890', position='CEO', departmentId=132, projectId=null, companyId=9, employeeId='google_2'}roles: employee";
        Company company = new Company(9L,"Google", "Technology", "California", "Sergey Brin, Larry Page");
        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
        when(redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token)).thenReturn(cachedInfo);
        String url = "/9/google_1/salary";
        //action
        int actualResult = verifyUser.authorizeUser(token,url,"post");
        //result
        Assert.assertEquals(0,actualResult);
        verify(redisService).getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token);
        verify(companyRepo).findById(anyLong());
    }

    @Test
    public void test_CompanyIdNotMatcheswithToken_AuthorizeUser_fails(){
        //employee Id not matches with token
        String token = "2-f2345650-ec3b-4d2e-96fd-6e34dca77df7-2";
        String cachedInfo = "Employee{id=15, name='Larry Page', dob='5/10/1989', permanentAdd='Chennai', currentAdd='California', phoneNumber='1234567890', position='CEO', departmentId=132, projectId=null, companyId=9, employeeId='google_2'}roles: employee";
        Company company = new Company(9L,"Google", "Technology", "California", "Sergey Brin, Larry Page");
        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
        when(redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token)).thenReturn(cachedInfo);
        String url = "/9/google_2/salary";
        //action
        int actualResult = verifyUser.authorizeUser(token,url,"post");
        //result
        Assert.assertEquals(0,actualResult);
        verify(redisService).getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token);
        verify(companyRepo).findById(anyLong());
    }

    @Test
    public void test_Employee_AuthorizeUser_Success(){
        //employee Id not matches with token
        String token = "2-f2345650-ec3b-4d2e-96fd-6e34dca77df7-9";
        String cachedInfo = "Employee{id=15, name='Larry Page', dob='5/10/1989', permanentAdd='Chennai', currentAdd='California', phoneNumber='1234567890', position='CEO', departmentId=132, projectId=null, companyId=9, employeeId='google_2'}roles: employee";
        Company company = new Company(9L,"Google", "Technology", "California", "Sergey Brin, Larry Page");
        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
        when(redisService.getKeyValue(anyString())).thenReturn(cachedInfo);
        String url = "9/google_2/salary";
        //action
        int actualResult = verifyUser.authorizeUser(token,url,"get");
        //result
        Assert.assertEquals(1,actualResult);
        verify(redisService).getKeyValue(StringConstant.ACCESS_TOKEN_REGEX+token);
        verify(companyRepo).findById(anyLong());
    }

}
