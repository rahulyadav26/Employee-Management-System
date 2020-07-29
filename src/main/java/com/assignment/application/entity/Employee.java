package com.assignment.application.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="employee")
public class Employee implements Serializable {

    @Id
    @Column(name="id")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="dob")
    private String dob;
    @Column(name="permanent_add")
    private String permanentAdd;
    @Column(name="current_add")
    private String currentAdd;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="position")
    private String position;
    @Column(name="dept_id")
    private Long departmentId;
    @Column(name="project_id")
    private Long projectId;
    @Column(name="comp_id")
    private Long companyId;
    @Column(name="emp_id" , columnDefinition = "text")
    private String employeeId;
    @OneToOne
    @JoinColumn(name="dept_id", referencedColumnName = "id",insertable=false, updatable=false)
    private Department department;

    public Employee(){

    }

    public Employee(String name, String dob, String permanentAdd, String currentAdd, String phoneNumber, String position, Long departmentId, Long projectId, Long companyId, String employeeId) {
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

    @Override
    public String toString() {
        return "Employee{" +
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
                '}';
    }
}
