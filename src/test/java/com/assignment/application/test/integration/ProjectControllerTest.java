package com.assignment.application.test.integration;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.TaskAppApplication;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Project;
import org.apache.kafka.common.protocol.types.Field;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskAppApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

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
    public void test_addProject_success(){
        setHttpHeaders(httpHeaders);
        Project project = new Project(63L,9L, 9L, "ABCD", "Sundar Pichai", "google_3");
        HttpEntity<Project> entity = new HttpEntity<>(project,httpHeaders);
        ResponseEntity<Project> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//9/project",
                HttpMethod.POST,entity,Project.class);
        Assert.assertEquals(project.getCompanyId(),responseEntity.getBody().getCompanyId());
        Assert.assertEquals(project.getDepartmentId(),responseEntity.getBody().getDepartmentId());
        Assert.assertEquals(project.getDescription(),responseEntity.getBody().getDescription());
        Assert.assertEquals(project.getTeamLead(),responseEntity.getBody().getTeamLead());
        Assert.assertEquals(project.getTeamLeadId(),responseEntity.getBody().getTeamLeadId());
    }

    @Test
    public void test_getProject_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<Project> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<List<Project>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//9/project",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Project>>() {});
        Assert.assertEquals(1,responseEntity.getBody().size());
    }

    @Test
    public void test_deleteProject_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<Project> entity = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//9/project/63",
                HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,responseEntity.getBody());
    }

}
