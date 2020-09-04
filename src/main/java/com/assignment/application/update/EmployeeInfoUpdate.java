package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeInfoUpdate implements Serializable {

    @JsonProperty(value = "current_address")
    private String currentAddress;

    @JsonProperty(value = "permanent_address")
    private String permanentAddress;

    public EmployeeInfoUpdate() {

    }

    public EmployeeInfoUpdate(String currentAddress, String permanentAddress) {
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

}
