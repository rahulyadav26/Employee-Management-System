package com.assignment.application.enums;

import com.assignment.application.exception.NotExistsException;

public enum RoleName {

    EMPLOYEE,
    ADMIN;

    public static String getRoleName(String name) {
        switch (name) {
            case "0":
                return EMPLOYEE.toString();
            case "1":
                return ADMIN.toString();
            default:
                throw new NotExistsException("No such role name exists");
        }
    }

}
