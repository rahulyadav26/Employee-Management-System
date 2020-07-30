package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryEmployeeUpdate implements Serializable {

    @JsonProperty(value = "value")
    private Double value;

    public SalaryEmployeeUpdate(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public SalaryEmployeeUpdate() {
    }
}
