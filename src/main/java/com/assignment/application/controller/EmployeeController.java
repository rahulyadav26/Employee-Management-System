package com.assignment.application.controller;

import com.assignment.application.entity.Employee;
import com.assignment.application.service.interfaces.EmployeeServiceInterface;
import com.assignment.application.update.employee.AddressUpdate;
import com.assignment.application.update.employee.PositionUpdate;
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

    @RequestMapping(value="/{company_id}/{emp_id}/address-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateCurrAddress(@PathVariable("emp_id") String emp_id,
                                                    @PathVariable("company_id") long companyId,
                                                    @RequestBody AddressUpdate addressUpdate){
        return employeeServiceInterface.updateAddress(emp_id,companyId,addressUpdate);
    }

    @RequestMapping(value="/{company_id}/{emp_id}/position-update" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updatePosition(@PathVariable("emp_id") String emp_id,
                                                 @PathVariable("company_id") long companyId,
                                                 @RequestBody PositionUpdate positionUpdate){
        System.out.println(emp_id + " " + companyId + " " + positionUpdate.getPosition());
        return employeeServiceInterface.updatePosition(emp_id,companyId,positionUpdate);
    }

}
