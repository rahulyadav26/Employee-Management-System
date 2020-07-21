package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentInfoUpdate {

    @JsonProperty(value="head")
    private String head;

    @JsonProperty(value="employee_count")
    private String employeeCount;

    @JsonProperty(value="ongoing_project")
    private String ongoingProject;

    @JsonProperty(value="completed_project")
    private String completedProject;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getOngoingProject() {
        return ongoingProject;
    }

    public void setOngoingProject(String ongoingProject) {
        this.ongoingProject = ongoingProject;
    }

    public String getCompletedProject() {
        return completedProject;
    }

    public void setCompletedProject(String completedProject) {
        this.completedProject = completedProject;
    }
}
