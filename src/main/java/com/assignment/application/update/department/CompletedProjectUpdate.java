package com.assignment.application.update.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompletedProjectUpdate {

    @JsonProperty(value="completed_project")
    long completed_project;

    public long getCompleted_project() {
        return completed_project;
    }
}
