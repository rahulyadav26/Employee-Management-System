package com.assignment.application.update.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionUpdate {

    @JsonProperty(value = "position")
    String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
