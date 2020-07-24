package com.assignment.application.kafka;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import com.assignment.application.entity.Salary;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerListener {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private StringConstant stringConstants;

    @KafkaListener(topics = "employeeUpdate", groupId = "employee",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public String consumerEmployeeInfo(KafkaEmployee kafkaEmployee) {
        Employee employee = employeeRepo.getEmployee(kafkaEmployee.getEmployeeId());
        if(kafkaEmployee==null){
            return stringConstants.invalidStatus;
        }
        if (employee == null) {
            employee = new Employee();
            employee.setId(kafkaEmployee.getId());
            employee.setName(kafkaEmployee.getName());
            employee.setPhoneNumber(kafkaEmployee.getPhoneNumber());
            employee.setPosition(kafkaEmployee.getPosition());
            employee.setPermanentAdd(kafkaEmployee.getPermanentAdd());
            employee.setCurrentAdd(kafkaEmployee.getCurrentAdd());
            employee.setCompanyId(kafkaEmployee.getCompanyId());
            employee.setDepartmentId(kafkaEmployee.getDepartmentId());
            employee.setEmployeeId(kafkaEmployee.getEmployeeId());
            employee.setProjectId(kafkaEmployee.getProjectId());
            employee.setDob(kafkaEmployee.getDob());
            Company company = companyRepo.findById(kafkaEmployee.getCompanyId()).orElse(null);
            if (company == null) {
                return stringConstants.invalidStatus;
            }
            cachingInfo.addEmployee(employee, kafkaEmployee.getCompanyId());
            salaryRepo.save(new Salary(kafkaEmployee.getEmployeeId(), kafkaEmployee.getName(), kafkaEmployee.getSalary(), kafkaEmployee.getAccNo(), kafkaEmployee.getCompanyId(), kafkaEmployee.getDepartmentId()));
            return stringConstants.savedInfo;
        }
        Company company = companyRepo.findById(kafkaEmployee.getCompanyId()).orElse(null);
        if (company == null) {
            return stringConstants.invalidStatus;
        }
        try {
            System.out.println("hey3");
            Salary salary = new Salary();
            System.out.println(kafkaEmployee.getEmployeeId());
            salary = salaryRepo.getSalaryById(kafkaEmployee.getEmployeeId());
            salaryRepo.deleteById(salary.getId());
            salary.setSalary(kafkaEmployee.getSalary());
            List<Salary> list = new ArrayList<>();
            list.add(salary);
            cachingInfo.updateSalary(list, company.getId());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return stringConstants.updateStatus;
    }

}
