package com.assignment.application.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyInfoUpdate {

    @JsonProperty("field_to_update")
    private String fieldToUpdate;
    @JsonProperty("updated_value")
    private String updatedValue;

    public String getFieldToUpdate() {
        return fieldToUpdate;
    }

    public String getUpdatedValue() {
        return updatedValue;
    }

    public void setFieldToUpdate(String fieldToUpdate) {
        this.fieldToUpdate = fieldToUpdate;
    }

    public void setUpdatedValue(String updatedValue) {
        this.updatedValue = updatedValue;
    }
}
