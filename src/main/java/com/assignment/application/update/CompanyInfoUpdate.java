package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CompanyInfoUpdate implements Serializable {

    @JsonProperty("industry_type")
    private String industryType;

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public CompanyInfoUpdate(String industryType) {
        this.industryType = industryType;
    }

    public CompanyInfoUpdate(){}
}
