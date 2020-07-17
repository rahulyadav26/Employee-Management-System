package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndustryUpdate {

    @JsonProperty("id")
    long id;
    @JsonProperty("industry_type")
    String industryType;

    public long getId() {
        return id;
    }

    public String getIndustryType() {
        return industryType;
    }
}
