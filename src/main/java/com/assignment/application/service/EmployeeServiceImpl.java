package com.assignment.application.service;

import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.entity.Employee;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeServiceI {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public ResponseEntity<Employee> addEmployee(Long id, Employee employee) {
        try{
            if(employee.getCompanyId()!=id || employee.getId()==0){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            return new ResponseEntity<>(employeeRepo.save(employee),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesOfComp(Long companyId) {
        try{
            List<Employee> employeeList = employeeRepo.getAllEmpByCompId(companyId);
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

    @Override
    public ResponseEntity<String> updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate) {
        try{
            Employee employee = employeeRepo.getEmployee(employeeId);
            if(employee.getCompanyId()!=companyId){
                return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
            }
            if(!employeeInfoUpdate.getCurrentAddress().isEmpty()){
                employee.setCurrentAdd(employeeInfoUpdate.getCurrentAddress());
            }
            if(!employeeInfoUpdate.getPermanentAddress().isEmpty()){
                employee.setPermanentAdd(employeeInfoUpdate.getPermanentAddress());
            }
            if(!employeeInfoUpdate.getPosition().isEmpty()){
                employee.setPosition(employeeInfoUpdate.getPosition());
            }
            employeeRepo.save(employee);
            return new ResponseEntity<>("Updated Permanent Address",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployee(Long companyId, String employeeId) {
        try{
            Employee employee = employeeRepo.getEmployee(employeeId);
            if(companyId!=employee.getCompanyId()){
                return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
            }
            employeeRepo.delete(employee);
            return new ResponseEntity<>("Deletion Successful",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while deleting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
