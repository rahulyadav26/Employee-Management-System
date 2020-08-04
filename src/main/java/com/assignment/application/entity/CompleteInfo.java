package com.assignment.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteInfo implements Serializable {

    private String employeeName;

    private String employeeId;

    private Long companyId;

    private String departmentName;

    private Long departmentId;

    private Double salary;

    private String currentAdd;

    private String permanentAdd;

    private String phoneNumber;
}
