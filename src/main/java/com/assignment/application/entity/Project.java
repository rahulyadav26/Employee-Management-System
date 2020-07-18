package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name="id")
    long id;
    @Column(name="dept_id")
    long dept_id;
    @Column(name="comp_id")
    long comp_id;
    @Column(name="description")
    String description;
    @Column(name="team_lead")
    String team_lead;
    @Column(name="team_lead_id")
    String team_lead_id;

    public Project(){

    }

    public Project(long id, long dept_id, long comp_id, String description, String team_lead, String team_lead_id) {
        this.id = id;
        this.dept_id = dept_id;
        this.comp_id = comp_id;
        this.description = description;
        this.team_lead = team_lead;
        this.team_lead_id = team_lead_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDept_id() {
        return dept_id;
    }

    public void setDept_id(long dept_id) {
        this.dept_id = dept_id;
    }

    public long getComp_id() {
        return comp_id;
    }

    public void setComp_id(long comp_id) {
        this.comp_id = comp_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeam_lead() {
        return team_lead;
    }

    public void setTeam_lead(String team_lead) {
        this.team_lead = team_lead;
    }

    public String getTeam_lead_id() {
        return team_lead_id;
    }

    public void setTeam_lead_id(String team_lead_id) {
        this.team_lead_id = team_lead_id;
    }
}
