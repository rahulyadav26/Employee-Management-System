package com.assignment.application.update.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeptHeadUpdate {

    @JsonProperty(value="head_id")
    String id;
    @JsonProperty(value="head")
    String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
