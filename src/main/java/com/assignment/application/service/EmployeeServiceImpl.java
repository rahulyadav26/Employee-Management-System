package com.assignment.application.service;

import com.assignment.application.update.employee.AddressUpdate;
import com.assignment.application.entity.Employee;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeServiceInterface;
import com.assignment.application.update.employee.PositionUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @Override
    public ResponseEntity<String> updateAddress(String emp_id, long companyId, AddressUpdate addressUpdate) {
        try{
            employee = employeeRepo.getEmployee(emp_id);
            if(employee.getComp_id()!=companyId){
                return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
            }
            if(addressUpdate.getType().equals("permanent")){
                employee.setPermanent_add(addressUpdate.getAddress());
                employeeRepo.save(employee);
                return new ResponseEntity<>("Updated Permanent Address",HttpStatus.OK);
            }
            else{
                employee.setCurrent_add(addressUpdate.getAddress());
                employeeRepo.save(employee);
                return new ResponseEntity<>("Updated Current Address",HttpStatus.OK);
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updatePosition(String emp_id, long companyId, PositionUpdate positionUpdate) {
        try{
            employee = employeeRepo.getEmployee(emp_id);
            if(employee.getComp_id()!=companyId){
                return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
            }
            employee.setPosition(positionUpdate.getPosition());
            employeeRepo.save(employee);
            return new ResponseEntity<>("Update Successful",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployee(long companyId, String emp_id) {
        try{
            employee = employeeRepo.getEmployee(emp_id);
            if(companyId!=employee.getComp_id()){
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
