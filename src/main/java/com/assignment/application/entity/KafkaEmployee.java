package com.assignment.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaEmployee implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "dob")
    private String dob;

    @JsonProperty(value = "permanent_add")
    private String permanentAdd;

    @JsonProperty(value = "current_add")
    private String currentAdd;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "position")
    private String position;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "project_id")
    private Long projectId;

    @JsonProperty(value = "company_id")
    private Long companyId;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    @JsonProperty(value = "salary")
    private Double salary;

    @JsonProperty(value = "acc_no")
    private String accNo;

    public KafkaEmployee() {

    }

    public KafkaEmployee(Long id, String name, String dob, String permanentAdd, String currentAdd, String phoneNumber,
                         String position, Long departmentId, Long projectId, Long companyId, String employeeId,
                         Double salary, String accNo) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.permanentAdd = permanentAdd;
        this.currentAdd = currentAdd;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.departmentId = departmentId;
        this.projectId = projectId;
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.salary = salary;
        this.accNo = accNo;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public String getCurrentAdd() {
        return currentAdd;
    }

    public void setCurrentAdd(String currentAdd) {
        this.currentAdd = currentAdd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
}
