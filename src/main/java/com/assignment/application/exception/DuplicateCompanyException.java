package com.assignment.application.exception;

public class DuplicateCompanyException extends RuntimeException{
    public DuplicateCompanyException(String message){
        super(message);
    }
}
