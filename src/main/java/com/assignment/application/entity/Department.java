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
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "head")
    private String head;

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

    @Column(name = "is_active")
    private Long isActive = 1L;

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DepartmentList departmentList;

    public Department() {
    }

    public Department(Long companyId, Long departmentId, String head, Date createdAt, Date updatedAt, String createdBy,
                      String updatedBy, Long isActive) {
        this.companyId = companyId;
        this.departmentId = departmentId;
        this.head = head;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
    }

    public Department(Long id, Long companyId, Long departmentId, String head, Date createdAt, Date updatedAt,
                      String createdBy, String updatedBy, Long isActive) {
        this.id = id;
        this.companyId = companyId;
        this.departmentId = departmentId;
        this.head = head;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getIsActive() {
        return isActive;
    }

    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }
}
