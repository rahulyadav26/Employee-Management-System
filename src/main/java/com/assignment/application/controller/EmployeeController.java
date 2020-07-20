package com.assignment.application.controller;

import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceI employeeServiceI;

    @PostMapping(value="/{company_id}/employee")
    public ResponseEntity<Employee> addEmployee(@PathVariable("company_id") Long companyId,@RequestBody Employee employee){
        return employeeServiceI.addEmployee(companyId,employee);
    }

    @GetMapping(value="{company_id}/employee")
    public ResponseEntity<List<Employee>> getEmployeesOfComp(@PathVariable("company_id") Long companyId){
        return employeeServiceI.getEmployeesOfComp(companyId);
    }

    @GetMapping(value="/employee")
    public ResponseEntity<List<Employee>> getEmployees(){
        return employeeServiceI.getEmployees();
    }

    @PatchMapping(value="/{company_id}/{emp_id}/update-employee-info")
    public ResponseEntity<String> updateEmployeeInfo(@PathVariable("emp_id") String employeeId,
                                                    @PathVariable("company_id") Long companyId,
                                                    @RequestBody EmployeeInfoUpdate employeeInfoUpdate){
        return employeeServiceI.updateEmployeeInfo(employeeId,companyId, employeeInfoUpdate);
    }

    @DeleteMapping(value="{company_id}/{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("company_id") Long companyId,
                                                 @PathVariable("emp_id") String empId){
        return employeeServiceI.deleteEmployee(companyId,empId);
    }

}
