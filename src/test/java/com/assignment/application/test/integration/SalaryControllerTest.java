package com.assignment.application.test.integration;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.TaskAppApplication;
import com.assignment.application.entity.Salary;
import com.assignment.application.update.SalaryUpdate;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskAppApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaryControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        httpHeaders.add("access_token","f9662650-ec3b-4d2e-96fd-6e29b8a77df7");
        this.httpHeaders = httpHeaders;
    }

    @Test
    public void test_addSalary_success(){
        setHttpHeaders(httpHeaders);
        Salary salary = new Salary("google_1",100000d,"12343234323444",9L,9L);
        HttpEntity<Salary> entity = new HttpEntity<>(salary,httpHeaders);
        ResponseEntity<Salary> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//9/google_1/salary",
                HttpMethod.POST,entity,Salary.class);
        Assert.assertEquals(salary.getSalary(),responseEntity.getBody().getSalary());
        Assert.assertEquals(salary.getCompanyId(),responseEntity.getBody().getCompanyId());
        Assert.assertEquals(salary.getEmployeeId(),responseEntity.getBody().getEmployeeId());
        Assert.assertEquals(salary.getAccountNo(),responseEntity.getBody().getAccountNo());
        Assert.assertEquals(salary.getDepartmentId(),responseEntity.getBody().getDepartmentId());
    }

    @Test
    public void test_GetEmployeeSalary_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<Salary> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<Salary> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//9/google_3/salary",
                HttpMethod.GET, entity, Salary.class);
        Salary salary = new Salary("google_3",10067000d,"12323232345656",9L,9L);
        Assert.assertEquals(salary.getEmployeeId(),responseEntity.getBody().getEmployeeId());
        Assert.assertEquals(salary.getSalary(),responseEntity.getBody().getSalary());
    }

    @Test
    public void test_UpdateSalaryCompAmount_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        SalaryUpdate salaryUpdate = new SalaryUpdate("0","0","",9L,100);
        HttpEntity<SalaryUpdate> entity = new HttpEntity<>(salaryUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//9/salary-update",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_UpdateSalaryCompPercent_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        SalaryUpdate salaryUpdate = new SalaryUpdate("0","1","",9L,10);
        HttpEntity<SalaryUpdate> entity = new HttpEntity<>(salaryUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//9/salary-update",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_UpdateSalaryCompDeptAmount_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        SalaryUpdate salaryUpdate = new SalaryUpdate("1","0","Growth",9L,100);
        HttpEntity<SalaryUpdate> entity = new HttpEntity<>(salaryUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//9/salary-update",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_UpdateSalaryCompDeptPercent_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        SalaryUpdate salaryUpdate = new SalaryUpdate("1","1","Growth",9L,10);
        HttpEntity<SalaryUpdate> entity = new HttpEntity<>(salaryUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//9/salary-update",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

}
