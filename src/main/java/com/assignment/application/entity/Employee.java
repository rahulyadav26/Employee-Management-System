package com.assignment.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String dob;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "employee_type")
    private String employeeType = "1";

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "role_name")
    private String roleName = "employee";

    @Column(name = "is_active")
    private Long isActive = 1L;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy = "0";

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Department department;

    public Employee() {

    }

    public Employee(Long id, String name, String dob, String permanentAddress, String currentAddress,
                    String uniqueId, String employeeType, Long departmentId, String employeeId, String roleName,
                    Long isActive, Date createdAt, String createdBy, Date updatedAt, String updatedBy) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.permanentAddress = permanentAddress;
        this.currentAddress = currentAddress;
        this.uniqueId = uniqueId;
        this.employeeType = employeeType;
        this.departmentId = departmentId;
        this.employeeId = employeeId;
        this.roleName = roleName;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public Long getIsActive() {
        return isActive;
    }

    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", dob='" + dob + '\'' +
               ", permanentAddress='" + permanentAddress + '\'' +
               ", currentAddress='" + currentAddress + '\'' +
               ", uniqueId='" + uniqueId + '\'' +
               ", employeeType='" + employeeType + '\'' +
               ", departmentId=" + departmentId +
               ", employeeId='" + employeeId + '\'' +
               ", roleName='" + roleName + '\'' +
               ", isActive=" + isActive +
               ", createdAt=" + createdAt +
               ", createdBy='" + createdBy + '\'' +
               ", updatedAt=" + updatedAt +
               ", updatedBy='" + updatedBy + '\'' +
               '}';
    }
}
