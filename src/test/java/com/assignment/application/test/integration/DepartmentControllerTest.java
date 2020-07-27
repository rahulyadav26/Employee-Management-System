package com.assignment.application.test.integration;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.TaskAppApplication;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Department;
import com.assignment.application.update.CompanyInfoUpdate;
import com.assignment.application.update.DepartmentInfoUpdate;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskAppApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        httpHeaders.add("username","admin");
        httpHeaders.add("password","admin");
        this.httpHeaders = httpHeaders;
    }

    @Test
    public void test_AddDepartment_success(){
        setHttpHeaders(httpHeaders);
        Department department = new Department(53L, "Engineering", 5L, 1000L, 10L, 20L, "Ratan tata");
        HttpEntity<Department> entity = new HttpEntity<>(department,httpHeaders);
        ResponseEntity<Department> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//5/department",
                HttpMethod.POST,entity,Department.class);
        Assert.assertEquals(department.getHead(),responseEntity.getBody().getHead());
        Assert.assertEquals(department.getName(),responseEntity.getBody().getName());
        Assert.assertEquals(department.getCompanyId(),responseEntity.getBody().getCompanyId());
        Assert.assertEquals(department.getEmployeeCount(),responseEntity.getBody().getEmployeeCount());
    }

    @Test
    public void test_GetDepartmentOfComp_success(){
        setHttpHeaders(httpHeaders);
        Department department = new Department(10L, "Engineering", 5L, 1000L, 10L, 20L, "Ratan tata");
        HttpEntity<Department> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<Department> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//5/department/53",
                HttpMethod.GET,entity,Department.class);
        Assert.assertEquals(department.getHead(),responseEntity.getBody().getHead());
        Assert.assertEquals(department.getName(),responseEntity.getBody().getName());
        Assert.assertEquals(department.getCompanyId(),responseEntity.getBody().getCompanyId());
        Assert.assertEquals(department.getEmployeeCount(),responseEntity.getBody().getEmployeeCount());
    }

    @Test
    public void test_UpdateDepartmentInfo_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        DepartmentInfoUpdate departmentInfoUpdate = new DepartmentInfoUpdate("Sundar Pichai", "", "100", "");
        HttpEntity<DepartmentInfoUpdate> entity = new HttpEntity<>(departmentInfoUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//5/department/53/update-department",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_DeleteDepartment_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//5/department/53",
                HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,responseEntity.getBody());
    }

}
