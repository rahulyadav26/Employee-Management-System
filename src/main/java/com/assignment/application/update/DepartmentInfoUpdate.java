package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentInfoUpdate {

    @JsonProperty(value="head")
    private String head;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public DepartmentInfoUpdate(String head){
        this.head = head;
    }
}
