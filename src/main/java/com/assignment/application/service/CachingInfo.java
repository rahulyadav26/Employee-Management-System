package com.assignment.application.service;

import com.assignment.application.Constants.StringConstants;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.CompleteCompInfoRepo;
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

    @Autowired
    private StringConstants stringConstants;

    @Autowired
    private CompleteCompInfoRepo completeCompInfoRepo;

    @Cacheable(value="companyCompleteInfo" , key = "#companyId" , condition = "#result==null")
    public List<CompleteCompInfo> getCompanyCompleteInfo(Long companyId){
        List<CompleteCompInfo> companyInfoList = completeCompInfoRepo.companyCompleteList(companyId);
        return companyInfoList;
    }

    @Cacheable(value="companyEmployeeList" , key = "#companyId" , condition = "#result==null")
    public List<Employee> getEmployeeOfComp(Long companyId){
        List<Employee> employeesList = employeeRepo.getAllEmpByCompId(companyId);
        return employeesList;
    }

    @Caching(evict={@CacheEvict(value="companyEmployeeList" , key = "#companyId"),@CacheEvict(value="companyCompleteInfo" , key="#companyId")})
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate){
        Employee employee = employeeRepo.getEmployee(employeeId);
        if(employee==null || !employee.getCompanyId().equals(companyId)){
            return stringConstants.invalidStatus;
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
        return stringConstants.updateStatus;
    }

    @Caching(evict={@CacheEvict(value="companyEmployeeList" , key = "#companyId"),@CacheEvict(value="companyCompleteInfo" , key="#employee.companyId")})
    public Employee addEmployee(Employee employee,Long companyId){
        return employeeRepo.save(employee);
    }

    @CacheEvict(value="companyCompleteInfo" , key="#companyId")
    public void updateSalary(List<Salary> salaryList,Long companyId){
        salaryRepo.saveAll(salaryList);
    }

}
