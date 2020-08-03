package com.assignment.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "company_info")
public class Company implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "head_office")
    private String headOffice;

    @Column(name = "founder")
    private String founder;

    @Column(name = "is_active")
    private Long isActive = 1L;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy = "0";

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    public Company() {
    }

    public Company(String name, String industryType, String headOffice, String founder, Long isActive, Date createdAt,
                   String createdBy, Date updatedAt, String updatedBy) {
        this.name = name;
        this.industryType = industryType;
        this.headOffice = headOffice;
        this.founder = founder;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Company(Long id, String name, String industryType, String headOffice, String founder, Long isActive,
                   Date createdAt, String createdBy, Date updatedAt, String updatedBy) {
        this.id = id;
        this.name = name;
        this.industryType = industryType;
        this.headOffice = headOffice;
        this.founder = founder;
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

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
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
}
