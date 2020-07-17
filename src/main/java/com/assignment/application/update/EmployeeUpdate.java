package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeUpdate {

    @JsonProperty("id")
    long id;
    @JsonProperty("employee_count")
    long employeeCount;

    public long getId() {
        return id;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }
}
