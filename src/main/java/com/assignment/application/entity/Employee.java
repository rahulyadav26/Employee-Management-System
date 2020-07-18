package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name="employee")
public class Employee {

    @Id
    @Column(name="id")
    long id;
    @Column(name="name")
    String name;
    @Column(name="dob")
    String dob;
    @Column(name="permanent_add")
    String permanent_add;
    @Column(name="current_add")
    String current_add;
    @Column(name="phone_number")
    String phone_number;
    @Column(name="position")
    String position;
    @Column(name="dept_id")
    long dept_id;
    @Column(name="project_id")
    long project_id;
    @Column(name="comp_id")
    long comp_id;
    @Column(name="emp_id")
    String emp_id;

    public Employee(){

    }

    public Employee(long id, String name, String dob, String permanent_add, String current_add, String phone_number, String position, long dept_id, long project_id, long comp_id,String emp_id) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.permanent_add = permanent_add;
        this.current_add = current_add;
        this.phone_number = phone_number;
        this.position = position;
        this.dept_id = dept_id;
        this.project_id = project_id;
        this.comp_id = comp_id;
        this.emp_id = emp_id;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPermanent_add() {
        return permanent_add;
    }

    public void setPermanent_add(String permanent_add) {
        this.permanent_add = permanent_add;
    }

    public String getCurrent_add() {
        return current_add;
    }

    public void setCurrent_add(String current_add) {
        this.current_add = current_add;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getDept_id() {
        return dept_id;
    }

    public void setDept_id(long dept_id) {
        this.dept_id = dept_id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getComp_id() {
        return comp_id;
    }

    public void setComp_id(long comp_id) {
        this.comp_id = comp_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }
}
