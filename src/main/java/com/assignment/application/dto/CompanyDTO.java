package com.assignment.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "industry_type")
    private String industryType;

    @JsonProperty(value = "head_office")
    private String headOffice;

    @JsonProperty(value = "founder")
    private String founder;

    public CompanyDTO() {
    }

    public CompanyDTO(Long id, String name, String industryType, String headOffice, String founder) {
        this.id = id;
        this.name = name;
        this.industryType = industryType;
        this.headOffice = headOffice;
        this.founder = founder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }
}
