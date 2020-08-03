package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.enums.EmployeeType;
import com.assignment.application.enums.RoleName;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class EmployeeServiceImpl implements EmployeeServiceI {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private KafkaTemplate<String, EmployeeInfoUpdate> kafkaTemplateEmployeeUpdate;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    @Override
    public Employee addEmployee(Long companyId, Employee employee) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (employee == null || employee.getDepartmentId() == null || employee.getDob() == null ||
            employee.getName() == null || employee.getName().isEmpty() || employee.getPhoneNumber() == null
            || employee.getPhoneNumber().isEmpty() || employee.getPhoneNumber().length() != 10) {
            throw new InsufficientInformationException("Insufficient data found in request");
        }
        boolean isValid = Pattern.matches("\\d{10}", employee.getPhoneNumber());
        if (!isValid) {
            throw new InsufficientInformationException("Phone number not valid");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || !department.getCompanyId().equals(companyId) || department.getIsActive() == 0) {
            throw new NotExistsException("No such department exists for such company");
        }
        Employee checkEmployee =
                employeeRepo.getEmployee(Base64.getEncoder().encodeToString(employee.getDob().getBytes()),
                                         employee.getName().toUpperCase(), employee.getPhoneNumber());
        if (checkEmployee != null && checkEmployee.getIsActive() == 1) {
            throw new DuplicateDataException("Data Already Exists in database");
        }
        if (checkEmployee != null) {
            checkEmployee.setIsActive(1L);
            employeeRepo.save(checkEmployee);
            return checkEmployee;
        }
        //Employee employeeTemp = cachingInfo.addEmployee(employee);
        //kafkaTemplateEmployee.send(EMPLOYEE_INFORMATION_TOPIC, employee);
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setCreatedAt(new Date());
        employee.setCreatedBy("0");
        employee.setIsActive(1L);
        employee.setRoleName(RoleName.getRoleName(employee.getRoleName()));
        employee.setEmployeeType(EmployeeType.getEmployeeType(employee.getEmployeeType()));
        employee.setDob(Base64.getEncoder().encodeToString(employee.getDob().getBytes()));
        return employeeRepo.save(employee);
    }

    @Override
    public Page<Employee> getEmployeesOfComp(Long companyId, Pageable pageable) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        List<Long> departmentList = departmentRepo.getDepartmentOfCompany(companyId);
        return employeeRepo.getEmployeesOfCompany(departmentList, pageable);
    }

    @Override
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    @Override
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        if (employeeInfoUpdate == null) {
            throw new EmptyUpdateException("Employee Update information is null");
        }
        cachingInfo.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate);
        //kafkaTemplateEmployeeUpdate.send(EMPLOYEE_INFORMATION_TOPIC, employeeInfoUpdate);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteEmployee(Long companyId, String employeeId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException("No such employee exists");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException("Company Id not valid for the given employee");
        }
        cachingInfo.deleteEmployee(companyId, employeeId);
        //        String[] str = employeeId.split("_");
        //        redisService.deleteKey(StringConstant.ACCESS_TOKEN_GENERATED + employeeId);
        //        redisService.findAndDelete(StringConstant.ACCESS_TOKEN_REGEX + str[1],Long.toString(companyId));
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
