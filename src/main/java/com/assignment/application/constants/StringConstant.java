package com.assignment.application.constants;

import org.springframework.stereotype.Component;

@Component
public class StringConstant {

    public static final String FAILED = "Failed";

    public static final String INVALID_CREDENTIALS = "Invalid Credentials";

    public static final String UPDATE_SUCCESSFUL = "Update Successful";

    public static final String DELETION_SUCCESSFUL = "Deletion Successful";

    public static final String INFORMATION_SAVED_SUCCESSFULLY = "Information Saved Successfully";

    public static final String HOSTNAME = "127.0.0.1:9092";

    public static final String ACCESS_TOKEN_REGEX = "accessToken::";

    public static final String ACCESS_TOKEN_GENERATED = "generated::";

    public static final String USER_VERIFIED = "User Verified successfully";

    public static final String NO_SUCH_COMPANY_EXISTS = "No such company exists";

    public static final String NO_SUCH_EMPLOYEE_EXISTS = "No such employee exists";

    public static final String NO_SUCH_COMPANY_OR_DEPARTMENT_EXISTS = "No such company/department exists";

    public static final String COMPANY_ID_NOT_VALID_FOR_DEPARTMENT = "Company id not valid for the department";

    public static final String EMPLOYEE_INFORMATION_TOPIC = "EmployeeInformation";

    private StringConstant() {
    }

}
