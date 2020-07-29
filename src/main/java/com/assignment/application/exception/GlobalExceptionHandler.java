package com.assignment.application.exception;

import com.assignment.application.entity.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorisedAccessException.class)
    public ResponseEntity<?> unauthorizedAccessException(UnauthorisedAccessException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.toString(),
                "Unauthorized Access",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<?> duplicateDataException(DuplicateDataException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                "Data Already Exists in Database",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyDatabaseException.class)
    public ResponseEntity<?> emptyDatabaseException(EmptyDatabaseException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.toString(),
                "Data not found",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyUpdateException.class)
    public ResponseEntity<?> emptyUpdateException(EmptyUpdateException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.CONFLICT.toString(),
                "Update information missing",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<?> notExistsException(NotExistsException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.toString(),
                "Not found in database",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientInformationException.class)
    public ResponseEntity<?> insufficientDataExists(InsufficientInformationException exception,WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.CONFLICT.toString(),
                "Request doesn't contain expected data",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataMismatchException.class)
    public ResponseEntity<?> dataMisMatchException(DataMismatchException exception,WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),HttpStatus.CONFLICT.toString(),
                "Not valid data sent in request",exception.getMessage(),request.getContextPath());
        return new ResponseEntity<>(errorDetails,HttpStatus.CONFLICT);
    }

}
