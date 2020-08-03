package com.assignment.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "dob")
    @NotNull(message = "DOB cannot be null")
    @NotEmpty(message = "DOB cannot be empty")
    private LocalDate dob;

    @JsonProperty(value = "permanent_address")
    private String permanentAddress;

    @JsonProperty(value = "current_address")
    private String currentAddress;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "employee_type")
    private String employeeType;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    @NotNull(message = "Salary cannot be null")
    @NotEmpty(message = "Salary cannot be empty")
    @JsonProperty(value = "role_name")
    private String roleName;

    public EmployeeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
