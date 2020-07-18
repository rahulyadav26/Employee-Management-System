package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "family")
public class Family {

    @Id
    @Column(name = "id")
    long id;
    @Column(name = "relation")
    String relation;
    @Column(name = "occupation")
    String occupation;
    @Column(name = "phone_number")
    String phone_number;
    @Column(name = "address")
    String address;
    @Column(name = "emp_id")
    String emp_id;
    @Column(name = "name")
    String name;

    public Family(){

    }

    public Family(long id, String relation, String occupation, String phone_number, String address, String emp_id,String name) {
        this.id = id;
        this.relation = relation;
        this.occupation = occupation;
        this.phone_number = phone_number;
        this.address = address;
        this.emp_id = emp_id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
