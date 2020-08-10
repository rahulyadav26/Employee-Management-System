package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.enums.EmployeeType;
import com.assignment.application.exception.DataMismatchException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeService;
import com.assignment.application.update.EmployeeInfoUpdate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Component
public class EmployeeServiceImpl implements EmployeeService {

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

    @SneakyThrows
    @Override
    public Employee addEmployee(Long companyId, Employee employee, String userId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (companyId != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (employee.getEmployeeType().equals("1")) {
            if (companyId != 0 || employee.getDepartmentId() != null) {
                throw new InsufficientInformationException("Not valid data in body");
            }
        }
        if (employee == null || (employee.getDepartmentId() == null && employee.getEmployeeType().equals("0")) ||
            employee.getDob() == null) {
            throw new InsufficientInformationException("Insufficient data found in request");
        }
        boolean isValid = Pattern.matches("\\d{12}", employee.getUniqueId());
        if (!isValid) {
            throw new InsufficientInformationException("Phone number not valid");
        }
        Department department = null;
        if (employee.getEmployeeType().equals("0")) {
            department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        } else {
            employee.setDepartmentId(0L);
        }
        if (!employee.getEmployeeType().equals("1") && (department == null ||
                                                        !department.getCompanyId().equals(companyId) ||
                                                        department.getIsActive() == 0)) {
            employee.setEmployeeType(EmployeeType.getEmployeeType(employee.getEmployeeType()));
            throw new NotExistsException("No such department exists for such company");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date empDOB = formatter.parse(employee.getDob());
        long diff = (new Date().getTime() - empDOB.getTime());
        diff = TimeUnit.MILLISECONDS.toDays(diff) / 365L;
        if (diff < 18) {
            throw new InsufficientInformationException("Child labour is a crime. You are not 18 yet.");
        }
        kafkaTemplateEmployee.send(StringConstant.EMPLOYEE_INFORMATION_TOPIC, employee);
        return cachingInfo.addEmployee(companyId, employee, userId);
    }

    @Override
    public Page<Employee> getEmployeesOfComp(Long companyId, Pageable pageable) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        List<Long> departmentList = departmentRepo.getDepartmentOfCompany(companyId);
        cachingInfo.getEmployeeOfComp(departmentList, companyId);
        return employeeRepo.getEmployeesOfCompany(departmentList, pageable);
    }

    @Override
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    @Override
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate,
                                     String userId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (companyId != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException("No such company exists");
        }
        if (employeeInfoUpdate == null || ((employeeInfoUpdate.getCurrentAddress() == null ||
            employeeInfoUpdate.getCurrentAddress().isEmpty()) && (employeeInfoUpdate.getPermanentAddress() == null ||
            employeeInfoUpdate.getPermanentAddress().isEmpty()))) {
            throw new EmptyUpdateException("Check Employee Update information");
        }
        //kafkaTemplateEmployeeUpdate.send(EMPLOYEE_INFORMATION_TOPIC, employeeInfoUpdate);
        return cachingInfo.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate, userId);
    }

    @Override
    public String deleteEmployee(Long companyId, String employeeId, String userId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (companyId != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException("No such company exists");
        }
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException("No such employee exists");
        }
        if ((employee.getDepartmentId() == 0 && companyId != 0) ||
            (employee.getDepartmentId() != 0 && companyId == 0)) {
            throw new DataMismatchException("Company Id not valid for employee");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (employee.getDepartmentId() != 0 &&
            (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyId))) {
            throw new DataMismatchException("Company Id not valid for the given employee");
        }
        cachingInfo.deleteEmployee(companyId, employeeId, userId);
        redisService.deleteKey(StringConstant.ACCESS_TOKEN_GENERATED + employeeId);
        String[] employeeIdSplit = employeeId.split("-");
        redisService.findAndDelete(StringConstant.ACCESS_TOKEN_REGEX + employeeIdSplit[0],
                                   employeeIdSplit[employeeIdSplit.length - 1]);
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
