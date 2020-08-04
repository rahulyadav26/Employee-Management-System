package com.assignment.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "department_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentList implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private String createdBy = "0";

    @Column(name = "updated_by")
    private String updatedBy;

    public DepartmentList(String departmentName, Date createdAt, Date updatedAt, String createdBy, String updatedBy) {
        this.departmentName = departmentName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
