package com.assignment.application.enums;

import com.assignment.application.exception.NotExistsException;

public enum EmployeeType {

    FTE,
    FREELANCER;

    public static String getEmployeeType(String type) {
        switch (type) {
            case "0":
                return FTE.toString();
            case "1":
                return FREELANCER.toString();
            default:
                throw new NotExistsException("No such employee type exists");
        }
    }

}
