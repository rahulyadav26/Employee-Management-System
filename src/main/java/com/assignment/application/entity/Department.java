package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name="department")
public class Department {

    @Id
    @Column(name="id")
    long id;
    @Column(name="name")
    String name;
    @Column(name="comp_id")
    long comp_id;
    @Column(name="employee_count")
    long employee_count;
    @Column(name="ongoing_project")
    long ongoing_project;
    @Column(name="completed_project")
    long completed_project;
    @Column(name="head")
    String head;
    @Column(name="head_id")
    String head_id;

    public Department(){

    }

    public Department(long id, String name, long comp_id, long employee_count, long ongoing_project, long completed_project, String head, String head_id) {
        this.id = id;
        this.name = name;
        this.comp_id = comp_id;
        this.employee_count = employee_count;
        this.ongoing_project = ongoing_project;
        this.completed_project = completed_project;
        this.head = head;
        this.head_id = head_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getComp_id() {
        return comp_id;
    }

    public void setComp_id(long comp_id) {
        this.comp_id = comp_id;
    }

    public long getEmployee_count() {
        return employee_count;
    }

    public void setEmployee_count(long employee_count) {
        this.employee_count = employee_count;
    }

    public long getOngoing_project() {
        return ongoing_project;
    }

    public void setOngoing_project(long ongoing_project) {
        this.ongoing_project = ongoing_project;
    }

    public long getCompleted_project() {
        return completed_project;
    }

    public void setCompleted_project(long completed_project) {
        this.completed_project = completed_project;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead_id() {
        return head_id;
    }

    public void setHead_id(String head_id) {
        this.head_id = head_id;
    }
}
