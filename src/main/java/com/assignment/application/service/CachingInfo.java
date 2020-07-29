package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.EmployeeCacheInfo;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CachingInfo {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private EmployeeCacheInfo employeeCacheInfo;

    @Cacheable(value = "companyCompleteInfo", key = "#companyId", condition = "#result==null")
    public List<CompleteCompInfo> getCompanyCompleteInfo(Long companyId) {
        List<CompleteCompInfo> companyInfoList = companyRepo.getCompanyCompleteInfo(companyId);
        return companyInfoList;
    }

    @Cacheable(value = "companyEmployeeList", key = "#companyId", condition = "#result==null")
    public List<Employee> getEmployeeOfComp(Long companyId) {
        List<Employee> employeesList = employeeRepo.getAllEmpByCompId(companyId);
        return employeesList;
    }

    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"), @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        if (employee == null || !employee.getCompanyId().equals(companyId)) {
            return StringConstant.INVALID_CREDENTIALS;
        }
        if (!employeeInfoUpdate.getCurrentAddress().isEmpty()) {
            employee.setCurrentAdd(employeeInfoUpdate.getCurrentAddress());
        }
        if (!employeeInfoUpdate.getPermanentAddress().isEmpty()) {
            employee.setPermanentAdd(employeeInfoUpdate.getPermanentAddress());
        }
        if (!employeeInfoUpdate.getPosition().isEmpty()) {
            employee.setPosition(employeeInfoUpdate.getPosition());
        }
        if (!employeeInfoUpdate.getPhoneNumber().isEmpty()) {
            employee.setPhoneNumber(employeeInfoUpdate.getPhoneNumber());
        }
        employeeRepo.save(employee);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#employee.getCompanyId()"), @CacheEvict(value = "companyCompleteInfo", key = "#employee.companyId")})
    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @CacheEvict(value = "companyCompleteInfo", key = "#companyId")
    public void updateSalary(List<Salary> salaryList, Long companyId) {
        salaryRepo.saveAll(salaryList);
    }

    @Cacheable(value = "accessToken", key = "#token")
    public String tokenGenerate(String token , String username) {
        if(username.equalsIgnoreCase("superadmin")){
            String str = "roles: superadmin";
            return str;
        }
        Employee employee = employeeRepo.getEmployee(username);
        String str = employee.toString() + "roles: " + "employee";
        return str;
    }

    @Cacheable(value = "generated" , key = "#employeeId")
    public String updateTokenStatus(String employeeId){
        return "true";
    }

}
