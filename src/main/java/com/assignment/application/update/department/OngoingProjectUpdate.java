package com.assignment.application.update.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OngoingProjectUpdate {

    @JsonProperty(value="ongoing_project")
    long ongoing_project;

    public long getOngoing_project() {
        return ongoing_project;
    }
}
