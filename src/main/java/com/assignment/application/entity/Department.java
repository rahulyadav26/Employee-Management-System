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
    @Column(name="head")
    private String head;

    public Department(){

    }

    public Department(Long id, String name, Long companyId,String head) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.head = head;
    }

    public Department(String name, Long companyId, String head) {
        this.name = name;
        this.companyId = companyId;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
