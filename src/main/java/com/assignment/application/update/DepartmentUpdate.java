package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentUpdate implements Serializable {

    @JsonProperty(value = "company_id")
    private Long companyId;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "head")
    private String head;

    public DepartmentUpdate() {
    }

    public DepartmentUpdate(Long companyId, Long departmentId, String head) {
        this.companyId = companyId;
        this.departmentId = departmentId;
        this.head = head;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
