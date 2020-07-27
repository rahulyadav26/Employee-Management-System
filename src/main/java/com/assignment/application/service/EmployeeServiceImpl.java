package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.entity.Employee;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.interfaces.EmployeeServiceI;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Employee addEmployee(Long companyId, Employee employee) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (employee == null || company == null || !employee.getCompanyId().equals(companyId) ) {
            throw new RuntimeException("Data not valid");
        }
        Employee employeeTemp = cachingInfo.addEmployee(employee,companyId);
        return employeeTemp;
    }

    @Override
    public List<Employee> getEmployeesOfComp(Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            throw new RuntimeException("Data not valid");
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
        if (company == null || employeeInfoUpdate==null || cachingInfo.updateEmployeeInfo(employeeId,companyId, employeeInfoUpdate).equalsIgnoreCase(StringConstant.INVALID_CREDENTIALS)) {
            throw new RuntimeException("Data not valid");
        }
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteEmployee(Long companyId, String employeeId) {
        Employee employee = employeeRepo.getEmployee(employeeId);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company==null || employee == null || !companyId.equals(employee.getCompanyId())) {
            throw new RuntimeException("Data not Valid");
        }
        employeeRepo.delete(employee);
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
