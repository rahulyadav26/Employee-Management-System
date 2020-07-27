package com.assignment.application.Constants;

import org.springframework.stereotype.Component;

@Component
public class StringConstant {

    public static final String FAILED = "Failed";
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String UPDATE_SUCCESSFUL = "Update Successful";
    public static final String DELETION_SUCCESSFUL = "Deletion Successful";
    public static final String INFORMATION_SAVED_SUCCESSFULLY = "Information Saved Successfully";
    public static final String HOSTNAME = "127.0.0.1:9092";

    private StringConstant() {
    }

}
