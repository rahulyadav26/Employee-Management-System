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
public class DepartmentDTO implements Serializable {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "head")
    private String head;

    @JsonProperty(value = "company_id")
    private Long companyId;

    public DepartmentDTO(Long departmentId, String head) {
        this.departmentId = departmentId;
        this.head = head;
    }

}
