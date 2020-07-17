package com.assignment.application.update.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeptHeadUpdate {

    @JsonProperty(value="head_id")
    long id;
    @JsonProperty(value="head")
    String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
