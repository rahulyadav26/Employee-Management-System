package com.assignment.application.update.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressUpdate {

    @JsonProperty(value="type")
    String type;

    @JsonProperty(value="address")
    String address;

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }
}
