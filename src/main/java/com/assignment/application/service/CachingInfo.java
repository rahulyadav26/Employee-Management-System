package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.exception.DataMismatchException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CachingInfo {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    //@Cacheable(value = "companyCompleteInfo", key = "#companyId", condition = "#result==null")
    public Page<CompleteCompInfo> getCompanyCompleteInfo(Long companyId, Pageable pageable) {
        return companyRepo.getCompanyCompleteInfo(companyId, pageable);
    }

    //@Cacheable(value = "companyEmployeeList", key = "#companyId", condition = "#result==null")
    public List<Employee> getEmployeeOfComp(Long companyId) {
        return employeeRepo.getAllEmpByCompId(companyId);
    }

    //@Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"), @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException("No such employee exists");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0) {
            throw new NotExistsException("No such department exists");
        }
        if (!department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException("Company Id is not valid for the given employee");
        }
        if (!employeeInfoUpdate.getCurrentAddress().isEmpty()) {
            employee.setCurrentAddress(employeeInfoUpdate.getCurrentAddress());
        }
        if (!employeeInfoUpdate.getPermanentAddress().isEmpty()) {
            employee.setPermanentAddress(employeeInfoUpdate.getPermanentAddress());
        }
        employee.setUpdatedAt(new Date());
        employee.setUpdatedBy("0");
        employeeRepo.save(employee);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    //@Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#employee.getCompanyId()"), @CacheEvict(value = "companyCompleteInfo", key = "#employee.getCompanyId()")})
    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    //@CacheEvict(value = "companyCompleteInfo", key = "#companyId")
    public void updateSalary(List<Salary> salaryList, Long companyId) {
        salaryRepo.saveAll(salaryList);
    }

    //@Cacheable(value = "accessToken", key = "#token")
    public String tokenGenerate(String token, String username) {
        if (username.equalsIgnoreCase("superadmin")) {
            String str = "roles: superadmin";
            return str;
        }
        Employee employee = employeeRepo.getEmployee(username);
        String str = employee.toString() + "roles: " + "employee";
        return str;
    }

    //@Cacheable(value = "generated" , key = "#employeeId")
    public String updateTokenStatus(String employeeId) {
        return "true";
    }

    //@Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"), @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String deleteDepartment(Long companyId, Long departmentId) {
        departmentRepo.deleteById(departmentId);
        return StringConstant.DELETION_SUCCESSFUL;
    }

    //@Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"), @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String deleteEmployee(Long companyId, String employeeId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        employee.setIsActive(0L);
        employee.setUpdatedAt(new Date());
        employee.setUpdatedBy("0");
        employeeRepo.save(employee);
        return StringConstant.DELETION_SUCCESSFUL;
    }

}
