package com.assignment.application.entity;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    private Date date;

    private String status;

    private String error;

    private String message;

    private String path;

}
