package com.assignment.application.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="department")
public class Department implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="comp_id")
    private Long companyId;
    @Column(name="employee_count")
    private Long employeeCount;
    @Column(name="ongoing_project")
    private Long ongoingProject;
    @Column(name="completed_project")
    private Long completedProject;
    @Column(name="head")
    private String head;

    public Department(){

    }

    public Department(Long id, String name, Long companyId, Long employeeCount, Long ongoingProject, Long completedProject, String head) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.employeeCount = employeeCount;
        this.ongoingProject = ongoingProject;
        this.completedProject = completedProject;
        this.head = head;
    }

    public Department(String name, Long companyId, Long employeeCount, Long ongoingProject, Long completedProject, String head) {
        this.name = name;
        this.companyId = companyId;
        this.employeeCount = employeeCount;
        this.ongoingProject = ongoingProject;
        this.completedProject = completedProject;
        this.head = head;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Long getOngoingProject() {
        return ongoingProject;
    }

    public void setOngoingProject(Long ongoingProject) {
        this.ongoingProject = ongoingProject;
    }

    public Long getCompletedProject() {
        return completedProject;
    }

    public void setCompletedProject(Long completedProject) {
        this.completedProject = completedProject;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
