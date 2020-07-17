package com.assignment.application.entity;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="company_info")
@Component
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    long id;
    @Column(name="name")
    String name;
    @Column(name="industry_type")
    String industry_type;
    @Column(name="employee_count")
    long employee_count;
    @Column(name="head_office")
    String head_office;
    @Column(name="founder")
    String founder;
    @Column(name="founder_id")
    long founder_id;

    public Company(){

    }

    public Company(String name, String industry_type, long employee_count, String head_office, String founder, long founder_id) {
        this.name = name;
        this.industry_type = industry_type;
        this.employee_count = employee_count;
        this.head_office = head_office;
        this.founder = founder;
        this.founder_id = founder_id;
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

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public long getEmployee_count() {
        return employee_count;
    }

    public void setEmployee_count(long employee_count) {
        this.employee_count = employee_count;
    }

    public String getHead_office() {
        return head_office;
    }

    public void setHead_office(String head_office) {
        this.head_office = head_office;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public long getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(long founder_id) {
        this.founder_id = founder_id;
    }
}
