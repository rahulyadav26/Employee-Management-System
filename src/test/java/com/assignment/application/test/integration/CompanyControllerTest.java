package com.assignment.application.test.integration;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.TaskAppApplication;
import com.assignment.application.controller.CompanyController;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.exception.DuplicateDataException;
import com.assignment.application.update.CompanyInfoUpdate;
import net.bytebuddy.asm.Advice;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.kafka.common.protocol.types.Field;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskAppApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest {

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
    public void test_AddCompany_Success(){
        setHttpHeaders(httpHeaders);
        Company company = new Company("Apple", "Technology", 1000000L, "California", "Steve Jobs");
        HttpEntity<Company> entity = new HttpEntity<>(company,httpHeaders);
        ResponseEntity<Company> responseEntity = testRestTemplate.exchange("http://localhost:" + port +"//company",
                                                                            HttpMethod.POST,entity,Company.class);
        Assert.assertEquals(company.getHeadOffice(),responseEntity.getBody().getHeadOffice());
        Assert.assertEquals(company.getIndustryType(),responseEntity.getBody().getIndustryType());
        Assert.assertEquals(company.getFounder(),responseEntity.getBody().getFounder());
        Assert.assertEquals(company.getName(),responseEntity.getBody().getName());
    }

    @Test
    public void test_GetCompany_Success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        List<Company> companyList = new ArrayList<>();
        ResponseEntity<List<Company>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//company",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Company>>() {});
        Assert.assertEquals(6,responseEntity.getBody().size());
    }

    @Test
    public void test_GetCompleteCompInfo_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        List<CompleteCompInfo> companyList = new ArrayList<>();
        ResponseEntity<List<CompleteCompInfo>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//company/9/complete-info",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<CompleteCompInfo>>() {});
        Assert.assertEquals(1,responseEntity.getBody().size());
    }

    @Test
    public void test_UpdateCompanyInfo_success(){
        setHttpHeaders(httpHeaders);
        this.restTemplate = testRestTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        CompanyInfoUpdate companyInfoUpdate = new CompanyInfoUpdate("Search Engine Platform","");
        HttpEntity<CompanyInfoUpdate> entity = new HttpEntity<>(companyInfoUpdate,httpHeaders);
        List<CompleteCompInfo> companyList = new ArrayList<>();
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "//company/9/company-update",
                HttpMethod.PATCH, entity, String.class);
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,responseEntity.getBody());
    }

    @Test
    public void test_DeleteCompany_success(){
        setHttpHeaders(httpHeaders);
        HttpEntity<String> entity = new HttpEntity<>(null,httpHeaders);
        List<CompleteCompInfo> companyList = new ArrayList<>();
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "//company/15",
                HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,responseEntity.getBody());
    }

}
