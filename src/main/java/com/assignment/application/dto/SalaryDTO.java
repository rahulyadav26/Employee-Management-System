package com.assignment.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryDTO implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "salary")
    @NotNull
    private Double salary;

    @JsonProperty(value = "is_current")
    private Long isCurrent;

    public SalaryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Long isCurrent) {
        this.isCurrent = isCurrent;
    }
}
