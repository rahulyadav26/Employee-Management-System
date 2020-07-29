package com.assignment.application.test.unit;

import com.assignment.application.interceptor.AuthInterceptor;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthInterceptorTest {

    @InjectMocks
    private AuthInterceptor authInterceptor;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private RedisService redisService;

    @Mock
    private CompanyRepo companyRepo;

    @Test
    public void test_UrlIsNotSignUp_validateUrl_fails(){
        //url is not of signUp
        String url = "http://localhost:8081//2/oyo_4/salary";
        //action
        boolean actualResult = authInterceptor.validateUrl(url);
        //result
        Assert.assertEquals(false,actualResult);
        verify(companyRepo,times(0)).existsById(anyLong());
    }

    @Test
    public void test_UrlIsOfSignUpButCompanyNotExists_validateUrl_fails(){
        //url is of signUp but company not exists
        String url = "http://localhost:8081//company/9/signUp";
        when(companyRepo.findById(anyLong())).thenReturn(null);
        //action
        boolean actualResult = authInterceptor.validateUrl(url);
        //result
        Assert.assertEquals(false,actualResult);
        verify(companyRepo,times(1)).existsById(anyLong());
    }

    @Test
    public void test_validateUrl_Success(){
        //url is of signup type and company Exists
        String url = "http://localhost:8081//company/9/signUp";
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        //action
        boolean actualResult = authInterceptor.validateUrl(url);
        //result
        Assert.assertEquals(true,actualResult);
        verify(companyRepo).existsById(anyLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_RequestIsNotPost_PreHandle_fails() throws Exception{
        //request is not of post type and access token doesn't exists in header
        HttpServletResponse response = new MockHttpServletResponse();
        HttpServletRequest request = new MockHttpServletRequest();
        //action
        authInterceptor.preHandle(request, response,any(Object.class));
    }

}
