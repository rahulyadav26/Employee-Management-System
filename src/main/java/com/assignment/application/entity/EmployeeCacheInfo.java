package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class EmployeeCacheInfo implements Serializable {
    private Long id;

    private String name;

    private String dob;

    private String permanentAdd;

    private String currentAdd;

    private String phoneNumber;

    private String position;

    private Long departmentId;

    private Long projectId;

    private Long companyId;

    private String employeeId;

    private String role;

    public EmployeeCacheInfo() {

    }

    public EmployeeCacheInfo(String name, String dob, String permanentAdd, String currentAdd, String phoneNumber, String position, Long departmentId, Long projectId, Long companyId, String employeeId, String role) {
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
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "EmployeeCacheInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", permanentAdd='" + permanentAdd + '\'' +
                ", currentAdd='" + currentAdd + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position='" + position + '\'' +
                ", departmentId=" + departmentId +
                ", projectId=" + projectId +
                ", companyId=" + companyId +
                ", employeeId='" + employeeId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


