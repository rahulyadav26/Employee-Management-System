package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryUpdate implements Serializable {

    @JsonProperty(value = "type")
    String type;
    @JsonProperty(value = "subtype")
    String subType;
    @JsonProperty(value = "dept_name")
    String departmentName;
    @JsonProperty(value = "comp_id")
    long companyId;
    @JsonProperty(value = "value")
    long value;

    public SalaryUpdate() {
    }

    public SalaryUpdate(String type, String subType, String departmentName, long companyId, long value) {
        this.type = type;
        this.subType = subType;
        this.departmentName = departmentName;
        this.companyId = companyId;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
