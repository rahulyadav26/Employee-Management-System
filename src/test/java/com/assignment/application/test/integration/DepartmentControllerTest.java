package com.assignment.application.test.integration;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.TaskAppApplication;
import com.assignment.application.entity.Department;
import com.assignment.application.update.DepartmentInfoUpdate;
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
public class DepartmentControllerTest {

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
    public void test_AddDepartment_success(){
        setHttpHeaders(httpHeaders);
        Department department = new Department( "Engineering", 9L, "Sundar Pichai");
        HttpEntity<Department> entity = new HttpEntity<>(department,httpHeaders);
        ResponseEntity<Department> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//9/department",
                HttpMethod.POST,entity,Department.class);
        Assert.assertEquals(department.getHead(),responseEntity.getBody().getHead());
        Assert.assertEquals(department.getName(),responseEntity.getBody().getName());
        Assert.assertEquals(department.getCompanyId(),responseEntity.getBody().getCompanyId());
    }

    @Test
    public void test_GetDepartmentOfComp_success(){
        setHttpHeaders(httpHeaders);
        Department department = new Department(6L, "Marketing", 2L, "Ritesh Agarwal");
        HttpEntity<Department> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<Department> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//2/department/6",
                HttpMethod.GET,entity,Department.class);
        Assert.assertEquals(department.getHead(),responseEntity.getBody().getHead());
        Assert.assertEquals(department.getName(),responseEntity.getBody().getName());
        Assert.assertEquals(department.getCompanyId(),responseEntity.getBody().getCompanyId());
    }

    @Test
    public void test_UpdateDepartmentInfo_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        DepartmentInfoUpdate departmentInfoUpdate = new DepartmentInfoUpdate("Sundar Pichai");
        HttpEntity<DepartmentInfoUpdate> entity = new HttpEntity<>(departmentInfoUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//9/department/9/update-department",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_DeleteDepartment_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//132/department/9",
                HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,responseEntity.getBody());
    }

}
