package com.assignment.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class")
public class KafkaEmployee implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    @NotNull
    @NotEmpty
    private String name;

    @JsonProperty(value = "dob")
    private String dob;

    @JsonProperty(value = "permanent_address")
    private String permanentAddress;

    @JsonProperty(value = "current_address")
    private String currentAddress;

    @JsonProperty(value = "unique_id")
    @NotEmpty
    @NotNull
    private String uniqueId;

    @JsonProperty(value = "employee_type")
    @NotEmpty
    @NotNull
    private String employeeType;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    @JsonProperty(value = "roleName")
    private String roleName="0";

    @JsonProperty(value = "salary")
    @NotEmpty
    @NotNull
    private Double salary;

}
