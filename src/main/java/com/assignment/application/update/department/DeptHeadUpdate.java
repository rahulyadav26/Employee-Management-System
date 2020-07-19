package com.assignment.application.update.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeptHeadUpdate {

    @JsonProperty(value="head")
    String name;

    public String getName() {
        return name;
    }
}
