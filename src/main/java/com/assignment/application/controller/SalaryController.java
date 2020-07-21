package com.assignment.application.controller;


import com.assignment.application.entity.Salary;
import com.assignment.application.service.interfaces.SalaryServiceI;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryController {

    @Autowired
    private SalaryServiceI salaryServiceI;

    @Autowired
    private KafkaTemplate<String,SalaryUpdate> kafkaTemplateSalary;

    public final String TOPIC = "SalaryUpdate";

    @PostMapping(value="{comp_id}/{emp_id}/salary" )
    public ResponseEntity<Salary> addSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String employeeId,
                                                @RequestBody Salary salary){
        return salaryServiceI.addSalary(companyId,employeeId,salary);
    }

    @GetMapping(value="{comp_id}/{emp_id}/salary" )
    public ResponseEntity<Salary> getSalaryInfo(@PathVariable("comp_id") Long companyId,
                                                @PathVariable("emp_id") String empId){
        return salaryServiceI.getSalary(companyId,empId);
    }

    @GetMapping(value = "/salary")
    public ResponseEntity<List<Salary>> getSalaryList(){
        return salaryServiceI.getSalaryList();
    }

    @PatchMapping(value = "{comp_id}/salary-update")
    public ResponseEntity<String> updateSalary(@PathVariable("comp_id") Long companyId,
                                               @RequestBody SalaryUpdate salaryUpdate){
        kafkaTemplateSalary.send(TOPIC,salaryUpdate);
        return salaryServiceI.updateSalary(companyId,salaryUpdate);
    }

}
