package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="dept_id")
    private Long departmentId;
    @Column(name="comp_id")
    private Long companyId;
    @Column(name="description")
    private String description;
    @Column(name="team_lead")
    private String teamLead;
    @Column(name="team_lead_id")
    private String teamLeadId;

    public Project(){

    }

    public Project(Long departmentId, Long companyId, String description, String teamLead, String teamLeadId) {
        this.departmentId = departmentId;
        this.companyId = companyId;
        this.description = description;
        this.teamLead = teamLead;
        this.teamLeadId = teamLeadId;
    }

    public Project(Long id, Long departmentId, Long companyId, String description, String teamLead, String teamLeadId) {
        this.id = id;
        this.departmentId = departmentId;
        this.companyId = companyId;
        this.description = description;
        this.teamLead = teamLead;
        this.teamLeadId = teamLeadId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(String teamLead) {
        this.teamLead = teamLead;
    }

    public String getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(String teamLeadId) {
        this.teamLeadId = teamLeadId;
    }
}
