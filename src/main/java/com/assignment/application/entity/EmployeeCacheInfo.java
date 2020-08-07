package com.assignment.application.entity;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public EmployeeCacheInfo(String name, String dob, String permanentAdd, String currentAdd, String phoneNumber,
                             String position, Long departmentId, Long projectId, Long companyId, String employeeId,
                             String role) {
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


