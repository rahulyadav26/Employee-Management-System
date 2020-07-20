package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyInfoUpdate {

    @JsonProperty("industry_type")
    private String industryType;
    @JsonProperty("employee_count")
    private String employeeCount;

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }
}
