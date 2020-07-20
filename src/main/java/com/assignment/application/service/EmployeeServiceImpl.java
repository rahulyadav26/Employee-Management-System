package com.assignment.application.service;

import com.assignment.application.entity.Company;
import com.assignment.application.other.CachingInfo;
import com.assignment.application.repo.CompanyRepo;
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

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private CompanyRepo companyRepo;


    @Override
    public ResponseEntity<Employee> addEmployee(Long id, Employee employee) {
        try{
            Company company = companyRepo.getCompany(id);
            if(employee.getCompanyId()!=id || employee.getId()==0){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            Employee employeeTemp = cachingInfo.addEmployee(id,employee,company.getName());
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
            Company company = companyRepo.getCompany(companyId);
            List<Employee> employeeList = cachingInfo.getEmployeeOfComp(company.getName().toLowerCase(),company.getId());
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
            Company company = companyRepo.getCompany(companyId);
            if(cachingInfo.updateEmployeeInfo(employeeId,companyId,employeeInfoUpdate,company.getName().toLowerCase()).equalsIgnoreCase("Invalid Credentials")){
                return new ResponseEntity<>("Invalid Credentials",HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("Update Successful",HttpStatus.OK);
            }
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
