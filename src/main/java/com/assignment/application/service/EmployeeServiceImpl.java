package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.DataMismatchException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private KafkaTemplate<String, EmployeeInfoUpdate> kafkaTemplateEmployeeUpdate;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    public final String EMPLOYEE_INFORMATION_TOPIC = "EmployeeInformation";


    @Override
    public Employee addEmployee(Long companyId, Employee employee) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        if (employee == null || employee.getEmployeeId().isEmpty()) {
            throw new InsufficientInformationException("Insufficient data found in request");
        }
        if (!employee.getCompanyId().equals(companyId)) {
            throw new DataMismatchException("Company Id is not valid for the given employee");
        }
        Employee employeeTemp = cachingInfo.addEmployee(employee);
        kafkaTemplateEmployee.send(EMPLOYEE_INFORMATION_TOPIC, employee);
        return employeeTemp;
    }

    @Override
    public List<Employee> getEmployeesOfComp(Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        List<Employee> employeeList = cachingInfo.getEmployeeOfComp(company.getId());
        return employeeList;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employeeList = employeeRepo.findAll();
        return employeeList;
    }

    @Override
    public String updateEmployeeInfo(String employeeId, Long companyId, EmployeeInfoUpdate employeeInfoUpdate) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        if (employeeInfoUpdate == null){
            throw new EmptyUpdateException("Employee Update information is null");
        }
        cachingInfo.updateEmployeeInfo(employeeId, companyId, employeeInfoUpdate);
        kafkaTemplateEmployeeUpdate.send(EMPLOYEE_INFORMATION_TOPIC, employeeInfoUpdate);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteEmployee(Long companyId, String employeeId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new NotExistsException("No such company exists");
        }
        if (employee == null) {
            throw new NotExistsException("Insufficient data found in request");
        }
        if (!companyId.equals(employee.getCompanyId())) {
            throw new DataMismatchException("Company Id not valid for the given employee");
        }
        employeeRepo.delete(employee);
        redisService.deleteKey(StringConstant.ACCESS_TOKEN_GENERATED + employeeId);
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
