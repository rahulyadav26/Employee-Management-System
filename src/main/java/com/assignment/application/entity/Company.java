package com.assignment.application.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "company_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends CommonEntity implements Serializable{

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

    public Company(String name, String industryType, String headOffice, String founder, Long isActive) {
        this.name = name;
        this.industryType = industryType;
        this.headOffice = headOffice;
        this.founder = founder;
        this.isActive = isActive;
    }
}
