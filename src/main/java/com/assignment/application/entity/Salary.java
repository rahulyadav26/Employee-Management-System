package com.assignment.application.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name="salary")
public class Salary {

    @Id
    @Column(name="id")
    long id;
    @Column(name="emp_id")
    String emp_id;
    @Column(name="emp_name")
    String emp_name;
    @Column(name="salary")
    double salary;
    @Column(name="acc_no")
    String acc_no;
    @Column(name="comp_id")
    long comp_id;
    @Column(name="dept_id")
    long dept_id;

    public Salary(){

    }

    public Salary(long id, String emp_id, String emp_name, double salary, String acc_no, long comp_id, long dept_id) {
        this.id = id;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.salary = salary;
        this.acc_no = acc_no;
        this.comp_id = comp_id;
        this.dept_id = dept_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public long getComp_id() {
        return comp_id;
    }

    public void setComp_id(long comp_id) {
        this.comp_id = comp_id;
    }

    public long getDept_id() {
        return dept_id;
    }

    public void setDept_id(long dept_id) {
        this.dept_id = dept_id;
    }
}
