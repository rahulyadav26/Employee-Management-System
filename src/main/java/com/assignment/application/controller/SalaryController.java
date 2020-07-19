package com.assignment.application.controller;


import com.assignment.application.entity.Salary;
import com.assignment.application.service.interfaces.SalaryServiceInterface;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryController {

    @Autowired
    SalaryServiceInterface salaryServiceInterface;

    @RequestMapping(value="{comp_id}/{emp_id}/salary" , method = RequestMethod.POST)
    public ResponseEntity<Salary> addSalaryInfo(@PathVariable("comp_id") long compId,
                                                @PathVariable("emp_id") String empId,
                                                @RequestBody Salary salary){
        return salaryServiceInterface.addSalary(compId,empId,salary);
    }

    @RequestMapping(value="{comp_id}/{emp_id}/salary" , method = RequestMethod.GET)
    public ResponseEntity<Salary> getSalaryInfo(@PathVariable("comp_id") long compId,
                                                @PathVariable("emp_id") String empId){
        return salaryServiceInterface.getSalary(compId,empId);
    }

    @RequestMapping(value = "/salary" , method = RequestMethod.GET)
    public ResponseEntity<List<Salary>> getSalaryList(){
        return salaryServiceInterface.getSalaryList();
    }

    @RequestMapping(value = "{comp_id}/salary-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateSalary(@PathVariable("comp_id") long compId,
                                               @RequestBody SalaryUpdate salaryUpdate){
        return salaryServiceInterface.updateSalary(compId,salaryUpdate);
    }

}
