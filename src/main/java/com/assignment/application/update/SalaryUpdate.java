package com.assignment.application.update;

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
public class SalaryUpdate implements Serializable {

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "subtype")
    private String subType;

    @JsonProperty(value = "department_id")
    private Long departmentId;

    @JsonProperty(value = "value")
    private Long value;

}
