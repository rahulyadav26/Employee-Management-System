package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryUpdate implements Serializable {

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "subtype")
    private String subType;

    @JsonProperty(value = "dept_name")
    private String departmentName;

    @JsonProperty(value = "comp_id")
    private Long companyId;

    @JsonProperty(value = "value")
    private Long value;

    public SalaryUpdate() {
    }

    public SalaryUpdate(String type, String subType, String departmentName, Long companyId, Long value) {
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
