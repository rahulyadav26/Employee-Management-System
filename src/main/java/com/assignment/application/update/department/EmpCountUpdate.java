package com.assignment.application.update.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpCountUpdate {

    @JsonProperty(value="employee_count")
    long employee_count;

    public long getEmp_count() {
        return employee_count;
    }
}
