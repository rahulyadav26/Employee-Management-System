package com.assignment.application.exception;

public class UnauthorisedAccessException extends RuntimeException{

    public UnauthorisedAccessException(String message){
        super(message);
    }

}
