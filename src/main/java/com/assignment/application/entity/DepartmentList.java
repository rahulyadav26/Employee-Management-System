package com.assignment.application.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "department_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentList extends CommonEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    public DepartmentList(String departmentName) {
        this.departmentName = departmentName;
    }
}
