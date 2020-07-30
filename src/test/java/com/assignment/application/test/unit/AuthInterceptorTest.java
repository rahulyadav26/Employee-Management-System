package com.assignment.application.test.unit;

import com.assignment.application.entity.Employee;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.exception.AuthenticationException;
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

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsNotPostTokenMissing_PreHandle_fails() throws Exception{
        //request is not of post type and access token doesn't exists in header
        HttpServletResponse response = new MockHttpServletResponse();
        HttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        //action
        authInterceptor.preHandle(request, response,handler);
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsNotPostTokenNotInCache_PreHandle_fails() throws Exception{
        //request is not of post type and access token doesn't exists in cache
        HttpServletResponse response = new MockHttpServletResponse();
        HttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        //action
        authInterceptor.preHandle(request, response,handler);
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsOfPostTokenNotInCache_PreHandle_Success() throws Exception{
        //request is of post type and access token not exists in cache
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.addHeader("access_token","f2345650-ec3b-4d2e-96fd-6e34dca77df7");
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        Assert.assertEquals(true,actualResult);
        verify(redisService).getKeyValue(anyString());
    }

    @Test
    public void test_RequestIsNotPostTokenInCache_PreHandle_Success() throws Exception{
        //request is not of post type and access token exists in cache
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.addHeader("access_token","f2345650-ec3b-4d2e-96fd-6e34dca77df7");
        when(redisService.getKeyValue(anyString())).thenReturn("Employee{id=15, name='Larry Page', dob='5/10/1989', permanentAdd='Chennai', currentAdd='California', phoneNumber='1234567890', position='CEO', departmentId=132, projectId=null, companyId=9, employeeId='google_2'}roles: employee");
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        Assert.assertEquals(true,actualResult);
        verify(redisService).getKeyValue(anyString());
    }

    @Test
    public void test_RequestIsPostUrlIsNotOfSignUp_PreHandle_Success() throws Exception{
        //request is of post type but url is not of signUp
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.addHeader("access_token","f2345650-ec3b-4d2e-96fd-6e34dca77df7");
        request.setMethod("POST");
        request.setRequestURI(":8081//2/oyo_4/salary");
        when(redisService.getKeyValue(anyString())).thenReturn("Employee{id=15, name='Larry Page', dob='5/10/1989', permanentAdd='Chennai', currentAdd='California', phoneNumber='1234567890', position='CEO', departmentId=132, projectId=null, companyId=9, employeeId='google_2'}roles: employee");
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        Assert.assertEquals(true,actualResult);
        verify(redisService).getKeyValue(anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUp_PreHandle_fails() throws Exception{
        //request is of post type and url is of signup but headers are missing
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(authInterceptor).validateUrl(anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUpCompanyNotExist_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp but company not exists
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUpUserIsSuperAdmin_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp and user is superadmin but password not matches
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","superadmin");
        request.addHeader("password","MS8xLzE5OTU=");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(authInterceptor).validateUrl(anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUpUserIsSuperAdminPasswordCorrect_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp and user is superadmin and password matches. Admin token exists in cache
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","superadmin");
        request.addHeader("password","c3VwZXJhZG1pbg==");
        when(redisService.getKeyValue(anyString())).thenReturn("roles: superadmin");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(authInterceptor).validateUrl(anyString());
        verify(redisService).getKeyValue(anyString());
    }

    @Test
    public void test_RequestIsPostUrlIsSignUpUserIsSuperAdminPasswordCorrect_PreHandle_Success() throws Exception{
        //request is of post type and url is of signUp and user is superadmin and password matches. Admin token not exists in cache
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","superadmin");
        request.addHeader("password","c3VwZXJhZG1pbg==");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(companyRepo).existsById(anyLong());
    }

    @Test(expected = NotExistsException.class)
    public void test_RequestIsPostUrlIsSignUpUserIsEmployeeNotExist_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp and user is employee but not exists in database
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","abcde");
        request.addHeader("password","c3VwZXJhZG1pbg==");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(companyRepo).existsById(anyLong());
        verify(employeeRepo).getEmployee(anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUpUserIsEmployeeTokenExists_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp and user is employee and exists in db and token exists in cache
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","abcde");
        request.addHeader("password","c3VwZXJhZG1pbg==");
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_2");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
        when(redisService.getKeyValue(anyString())).thenReturn("2-f2345650-ec3b-4d2e-96fd-6e34dca77df7-9");
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(companyRepo).existsById(anyLong());
        verify(employeeRepo).getEmployee(anyString());
        verify(redisService).getKeyValue(anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUpUserIsEmployeeTokenNotExists_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp and user is employee and exists in db
        // and token not exists in cache. Password not matches
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","google_2");
        request.addHeader("password","c3VwZXJhZG1pbg==");
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,9L,"google_2");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(companyRepo).existsById(anyLong());
        verify(employeeRepo).getEmployee(anyString());
        verify(redisService).getKeyValue(anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void test_RequestIsPostUrlIsSignUpUserIsEmployeeTokenNotExistsCompanyIdNotMatches_PreHandle_fails() throws Exception{
        //request is of post type and url is of signUp and user is employee and exists in db
        // and token not exists in cache. Password matches but companyId not mached
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/2/signUp");
        request.addHeader("username","google_2");
        request.addHeader("password","MS8xLzE5OTU=");
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,9L,"google_2");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        verify(companyRepo).existsById(anyLong());
        verify(employeeRepo).getEmployee(anyString());
        verify(redisService).getKeyValue(anyString());
    }

    @Test
    public void test_RequestUrlIsPostEmployeeLogin_PreHandle_Success() throws Exception{
        //request is of post type and url is of signUp and user is employee and exists in db
        //and token not exists in cache.
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object handler = new Object();
        request.setMethod("POST");
        request.setRequestURI(":8081//company/9/signUp");
        request.addHeader("username","google_2");
        request.addHeader("password","MS8xLzE5OTU=");
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,9L,"google_2");
        when(companyRepo.existsById(anyLong())).thenReturn(true);
        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
        //action
        boolean actualResult = authInterceptor.preHandle(request, response,handler);
        //result
        Assert.assertEquals(true,actualResult);
        verify(companyRepo).existsById(anyLong());
        verify(employeeRepo).getEmployee(anyString());
        verify(redisService).getKeyValue(anyString());
    }

}
