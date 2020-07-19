package com.assignment.application.entity;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class CompleteCompInfo {


    long id;
    String emp_name;
    String emp_id;
    String comp_id;
    String dept_name;
    String dept_id;
    double salary;
    String project_id;
    String acc_no;
    String current_add;
    String permanent_add;
    String phone_number;

    public CompleteCompInfo(){

    }

    public CompleteCompInfo(String emp_name, String emp_id, String comp_id, String dept_name, String dept_id, double salary, String acc_no,String project_id,String phone_number, String current_add, String permanent_add) {
        this.emp_name = emp_name;
        this.emp_id = emp_id;
        this.comp_id = comp_id;
        this.dept_name = dept_name;
        this.dept_id = dept_id;
        this.salary = salary;
        this.project_id = project_id;
        this.acc_no = acc_no;
        this.current_add = current_add;
        this.permanent_add = permanent_add;
        this.phone_number = phone_number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }


    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getCurrent_add() {
        return current_add;
    }

    public void setCurrent_add(String current_add) {
        this.current_add = current_add;
    }

    public String getPermanent_add() {
        return permanent_add;
    }

    public void setPermanent_add(String permanent_add) {
        this.permanent_add = permanent_add;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
