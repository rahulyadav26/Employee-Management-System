package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.CompleteInfo;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.enums.EmployeeType;
import com.assignment.application.enums.RoleName;
import com.assignment.application.exception.DataMismatchException;
import com.assignment.application.exception.DuplicateDataException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Cacheable(value = "companyCompleteInfo", key = "#companyId", condition = "#result==null")
    public Page<CompleteInfo> getCompanyCompleteInfo(Long companyId, Pageable pageable) {
        return companyRepo.getCompanyCompleteInfo(companyId, pageable);
    }

    @Cacheable(value = "companyEmployeeList", key = "#companyId", condition = "#result==null")
    public List<Employee> getEmployeeOfComp(List<Long> departments, Long companyId) {
        return employeeRepo.getEmployeesOfCompany(departments);
    }

    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"),
                      @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate,
                                     String userId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException("No such employee exists");
        }
        if((employee.getDepartmentId()==0 && companyId!=0) || (employee.getDepartmentId()!=0 && companyId==0)){
            throw new DataMismatchException("Company Id not valid for employee");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (employee.getDepartmentId()!=0 && (department == null || department.getIsActive() == 0)) {
            throw new NotExistsException("No such department exists");
        }
        if (companyId!=0 && (!department.getCompanyId().equals(companyId))) {
            throw new DataMismatchException("Company Id is not valid for the given employee");
        }
        if (employeeInfoUpdate.getCurrentAddress() != null && !employeeInfoUpdate.getCurrentAddress().isEmpty()) {
            employee.setCurrentAddress(employeeInfoUpdate.getCurrentAddress());
        }
        if (employeeInfoUpdate.getPermanentAddress() != null && !employeeInfoUpdate.getPermanentAddress().isEmpty()) {
            employee.setPermanentAddress(employeeInfoUpdate.getPermanentAddress());
        }
        employee.setUpdatedAt(new Date());
        employee.setUpdatedBy(userId);
        employeeRepo.save(employee);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"),
                      @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public Employee addEmployee(Long companyId, Employee employee, String userId) {
        Employee checkEmployee =
                employeeRepo.getEmployeeByUniqueId(employee.getUniqueId());
        if (checkEmployee != null && checkEmployee.getIsActive() == 1) {
            throw new DuplicateDataException("Data Already Exists in database");
        }
        if (checkEmployee != null) {
            checkEmployee.setIsActive(1L);
            checkEmployee.setUpdatedAt(new Date());
            checkEmployee.setUpdatedBy(userId);
            checkEmployee.setDepartmentId(employee.getDepartmentId());
            checkEmployee.setRoleName(employee.getRoleName());
            if(employee.getEmployeeType().equals("0")) {
                employee.setRoleName(RoleName.getRoleName(employee.getRoleName()));
            }
            else{
                employee.setRoleName(RoleName.EMPLOYEE.toString());
            }
            checkEmployee.setEmployeeType(EmployeeType.getEmployeeType(employee.getEmployeeType()));
            employeeRepo.save(checkEmployee);
            return checkEmployee;
        }
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setCreatedBy(userId);
        employee.setIsActive(1L);
        if(employee.getEmployeeType().equals("0")) {
            employee.setRoleName(RoleName.getRoleName(employee.getRoleName()));
        }
        else{
            employee.setRoleName(RoleName.EMPLOYEE.toString());
        }
        employee.setEmployeeType(EmployeeType.getEmployeeType(employee.getEmployeeType()));
        employee.setDob(Base64.getEncoder().encodeToString(employee.getDob().getBytes()));
        employee.setCreatedAt(new Date());
        return employeeRepo.save(employee);
    }

    @Cacheable(value = "accessToken", key = "#token")
    public String tokenGenerate(String token, String username) {
        if (username.equalsIgnoreCase("superadmin")) {
            String str = "superadmin";
            return str;
        }
        Employee employee = employeeRepo.getEmployee(username);
        String str = employee.toString();
        return str;
    }

    @Cacheable(value = "generated", key = "#employeeId")
    public String updateTokenStatus(String employeeId) {
        return "true";
    }

    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"),
                      @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String deleteEmployee(Long companyId, String employeeId, String userId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        employee.setIsActive(0L);
        employee.setUpdatedAt(new Date());
        employee.setUpdatedBy(userId);
        employeeRepo.save(employee);
        return StringConstant.DELETION_SUCCESSFUL;
    }

}
