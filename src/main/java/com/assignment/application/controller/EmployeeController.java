package com.assignment.application.controller;

import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeServiceInterface;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceInterface employeeServiceInterface;

    @RequestMapping(value="/{company_id}/employee" , method = RequestMethod.POST)
    public ResponseEntity<Employee> addEmployee(@PathVariable("company_id") long companyId,@RequestBody Employee employee){
        return employeeServiceInterface.addEmployee(companyId,employee);
    }

    @RequestMapping(value="{company_id}/employee" , method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getEmployeesOfComp(@PathVariable("company_id") long companyId){
        return employeeServiceInterface.getEmployeesOfComp(companyId);
    }

    @RequestMapping(value="/employee" , method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getEmployees(){
        return employeeServiceInterface.getEmployees();
    }

}
