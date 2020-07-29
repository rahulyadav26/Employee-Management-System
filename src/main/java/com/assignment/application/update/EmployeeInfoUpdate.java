package com.assignment.application.update;

import com.assignment.application.exception.EmptyUpdateException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeInfoUpdate {

    @JsonProperty(value="current_address")
    private String currentAddress;

    @JsonProperty(value = "permanent_address")
    private String permanentAddress;

    @JsonProperty(value = "position")
    private String position;

    @JsonProperty("phone_number")
    private String phoneNumber;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeInfoUpdate(){

    }

    public EmployeeInfoUpdate(String currentAddress, String permanentAddress, String position, String phoneNumber) {
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.position = position;
        this.phoneNumber = phoneNumber;
    }
}
