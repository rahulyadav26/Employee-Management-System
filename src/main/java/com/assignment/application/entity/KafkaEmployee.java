package com.assignment.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaEmployee implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "dob")
    private String dob;

    @JsonProperty(value = "permanent_add")
    private String permanentAdd;

    @JsonProperty(value = "current_add")
    private String currentAdd;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "position")
    private String position;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "project_id")
    private Long projectId;

    @JsonProperty(value = "company_id")
    private Long companyId;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    @JsonProperty(value = "salary")
    private Double salary;

    @JsonProperty(value = "acc_no")
    private String accNo;

}
