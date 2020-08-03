package com.assignment.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDTO implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "head")
    private String head;

    @JsonProperty(value = "company_id")
    private Long companyId;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long departmentId, String head) {
        this.departmentId = departmentId;
        this.head = head;
    }

    public DepartmentDTO(Long id, Long departmentId, String head, Long companyId) {
        this.id = id;
        this.departmentId = departmentId;
        this.head = head;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
