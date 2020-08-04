package com.assignment.application.dto;

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
public class DepartmentListDTO implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "department_name")
    private String departmentName;

    public DepartmentListDTO(String departmentName) {
        this.departmentName = departmentName;
    }

}
