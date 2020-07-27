package com.assignment.application.exception;

public class EmptyDatabaseException extends RuntimeException{
    public EmptyDatabaseException(String message){
        super(message);
    }
}
