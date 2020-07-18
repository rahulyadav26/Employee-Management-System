package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryUpdate {

    @JsonProperty(value = "type")
    String type;
    @JsonProperty(value = "subtype")
    String subType;
    @JsonProperty(value = "dept_name")
    String dept_name;
    @JsonProperty(value = "comp_id")
    long comp_id;
    @JsonProperty(value = "value")
    long value;

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getDept_name() {
        return dept_name;
    }

    public long getComp_id() {
        return comp_id;
    }

    public long getValue() {
        return value;
    }
}
