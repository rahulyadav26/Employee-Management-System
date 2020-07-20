package com.assignment.application.other;

import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CachingInfo {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Cacheable(value="companyCompleteInfo" , key = "#companyName" , condition = "#result==null")
    public List<Object> getCompanyCompleteInfo(String companyName,Long companyId){
        List<Object> companyInfoList = companyRepo.getCompDataSet(companyId);
        return companyInfoList;
    }

    @Cacheable(value="companyEmployeeList" , key = "#companyName" , condition = "#result==null")
    public List<Employee> getEmployeeOfComp(String companyName,Long companyId){
        List<Employee> employeesList = employeeRepo.getAllEmpByCompId(companyId);
        return employeesList;
    }

    @Caching(evict={@CacheEvict(value="companyEmployeeList" , key = "#companyName"),@CacheEvict(value="companyCompleteInfo" , key="#companyName")})
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate,String companyName){
        Employee employee = employeeRepo.getEmployee(employeeId);
        if(employee.getCompanyId()!=companyId){
            return "Invalid credentials";
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
        if(!employeeInfoUpdate.getPhoneNumber().isEmpty()){
            employee.setPhoneNumber(employeeInfoUpdate.getPhoneNumber());
        }
        employeeRepo.save(employee);
        return "Update Successful";
    }

    @Caching(evict={@CacheEvict(value="companyEmployeeList" , key = "#companyName"),@CacheEvict(value="companyCompleteInfo" , key="#companyName")})
    public Employee addEmployee(Long id, Employee employee,String companyName){
        return employeeRepo.save(employee);
    }

    @CacheEvict(value="companyCompleteInfo" , key="#companyName")
    public void updateSalary(List<Salary> salaryList,String companyName){
        salaryRepo.saveAll(salaryList);
    }

}
