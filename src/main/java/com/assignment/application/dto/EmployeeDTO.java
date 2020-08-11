package com.assignment.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "dob")
    private String dob;

    @JsonProperty(value = "permanent_address")
    private String permanentAddress;

    @JsonProperty(value = "current_address")
    private String currentAddress;

    @JsonProperty(value = "unique_id")
    @NotNull
    @NotEmpty
    private String uniqueId;

    @JsonProperty(value = "employee_type")
    @NotEmpty
    @NotNull
    private String employeeType;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "employee_id")
    private String employeeId;

    @NotNull(message = "Salary cannot be null")
    @NotEmpty(message = "Salary cannot be empty")
    @JsonProperty(value = "role_name")
    private String roleName;
}
