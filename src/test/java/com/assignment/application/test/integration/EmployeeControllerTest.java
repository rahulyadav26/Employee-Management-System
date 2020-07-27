package com.assignment.application.test.integration;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.TaskAppApplication;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.update.CompanyInfoUpdate;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
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
public class EmployeeControllerTest {

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
    public void test_addEmployee_success(){
        setHttpHeaders(httpHeaders);
        Employee employee = new Employee("Sergey Brin","1/1/1990","California","California","123323430","CEO Alphabet",9L,1L,9L,"google_2");
        HttpEntity<Employee> entity = new HttpEntity<>(employee,httpHeaders);
        ResponseEntity<Employee> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//9/employee",
                HttpMethod.POST,entity,Employee.class);
        Assert.assertEquals(employee.getEmployeeId(),responseEntity.getBody().getEmployeeId());
        Assert.assertEquals(employee.getCompanyId(),responseEntity.getBody().getCompanyId());
        Assert.assertEquals(employee.getCurrentAdd(),responseEntity.getBody().getCurrentAdd());
        Assert.assertEquals(employee.getPermanentAdd(),responseEntity.getBody().getPermanentAdd());
        Assert.assertEquals(employee.getName(),responseEntity.getBody().getName());
    }

    @Test
    public void test_GetEmployeeComp_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<Employee> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<List<Employee>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//9/employee",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Employee>>() {});
        Assert.assertEquals(3,responseEntity.getBody().size());
    }

    @Test
    public void test_UpdateEmployeeComp_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        EmployeeInfoUpdate employeeInfoUpdate = new EmployeeInfoUpdate("California","","","2222222222");
        HttpEntity<EmployeeInfoUpdate> entity = new HttpEntity<>(employeeInfoUpdate,httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//9/google_3/update-employee-info",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_DeleteEmployee_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//9/google_2",
                HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,responseEntity.getBody());
    }

}
