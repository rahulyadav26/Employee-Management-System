package com.assignment.application.kafka;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.*;
import com.assignment.application.exception.DataMismatchException;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
    private DepartmentRepo departmentRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    public final String EMPLOYEE_INFORMATION_TOPIC = "EmployeeInformation";

    @Autowired
    private KafkaTemplate<String, Salary> kafkaTemplateSalary;

    public final String SALARY_UPDATE_TOPIC = "SalaryUpdate";


    @KafkaListener(topics = "employeeAddition", groupId = "employee",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public String consumerEmployeeInfo(KafkaEmployee kafkaEmployee) {
        if (kafkaEmployee == null) {
            return StringConstant.INVALID_CREDENTIALS;
        }
        Employee employee = employeeRepo.getEmployee(kafkaEmployee.getEmployeeId());
        Company company = companyRepo.findById(kafkaEmployee.getCompanyId()).orElse(null);
        Department department = departmentRepo.findById(kafkaEmployee.getDepartmentId()).orElse(null);
        if (company == null || department==null) {
            throw new NotExistsException("No such company exists");
        }
        if(!department.getCompanyId().equals(company.getId())){
            throw new DataMismatchException("Company Id not valid for the department");
        }
        if(kafkaEmployee.getEmployeeId().isEmpty()){
            throw new  InsufficientInformationException("Employee id is missing");
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
            cachingInfo.addEmployee(employee);
            salaryRepo.save(new Salary(kafkaEmployee.getEmployeeId(), kafkaEmployee.getSalary(), kafkaEmployee.getAccNo(), kafkaEmployee.getCompanyId(), kafkaEmployee.getDepartmentId()));
            kafkaTemplateEmployee.send(EMPLOYEE_INFORMATION_TOPIC, employee);
            return StringConstant.INFORMATION_SAVED_SUCCESSFULLY;
        }
        Salary salary;
        salary = salaryRepo.getSalaryById(kafkaEmployee.getEmployeeId());
        salaryRepo.deleteById(salary.getId());
        salary.setSalary(kafkaEmployee.getSalary());
        List<Salary> list = new ArrayList<>();
        list.add(salary);
        cachingInfo.updateSalary(list, company.getId());
        kafkaTemplateSalary.send(SALARY_UPDATE_TOPIC, salary);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

}
