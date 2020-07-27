package com.assignment.application.authenticator;

import com.assignment.application.entity.Employee;
import com.assignment.application.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

@Component
public class VerifyUser {

    @Autowired
    private EmployeeRepo employeeRepo;

    public int authorizeUser(String username,String password){
        if(username.equalsIgnoreCase("admin") && username.equalsIgnoreCase("admin")){
            return 1;
        }
        return 0;
    }

    public int authorizeEmployee(String username,String password){
        Employee employee;
        employee = employeeRepo.getEmployee(username);
        if(employee==null || !employee.getDob().equalsIgnoreCase(password)){
            return 0;
        }
        return 1;
    }

}
