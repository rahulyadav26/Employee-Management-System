package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CompleteCompInfo implements Serializable {


    private String employeeName;
    private String employeeId;
    private Long companyId;
    private String departmentName;
    private Long departmentId;
    private Double salary;
    private Long projectId;
    private String accountNo;
    private String currentAdd;
    private String permanentAdd;
    private String phoneNumber;

    public CompleteCompInfo(){

    }

    public CompleteCompInfo(String employeeName, String employeeId, Long companyId, String departmentName, Long departmentId, Double salary, String accountNo,Long projectId,String phoneNumber,String currentAdd, String permanentAdd) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.departmentName = departmentName;
        this.departmentId = departmentId;
        this.salary = salary;
        this.projectId = projectId;
        this.accountNo = accountNo;
        this.currentAdd = currentAdd;
        this.permanentAdd = permanentAdd;
        this.phoneNumber = phoneNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCurrentAdd() {
        return currentAdd;
    }

    public void setCurrentAdd(String currentAdd) {
        this.currentAdd = currentAdd;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
