package com.assignment.application.service;

import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeServiceInterface {

    @Autowired
    private Employee employee;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public ResponseEntity<Employee> addEmployee(long id, Employee employee) {
        try{
            if(employee.getComp_id()!=id){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            this.employee = employeeRepo.save(employee);
            return new ResponseEntity<>(this.employee,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesOfComp(long company_id) {
        try{
            List<Employee> employeeList = employeeRepo.getAllEmpByCompId(company_id);
            return new ResponseEntity<>(employeeList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployees() {
        try{
            List<Employee> employeeList = employeeRepo.findAll();
            return new ResponseEntity<>(employeeList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
