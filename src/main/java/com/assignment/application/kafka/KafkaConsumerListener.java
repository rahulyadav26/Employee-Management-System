package com.assignment.application.kafka;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.CompanyRepo;
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
        Employee employee = employeeRepo.getEmployee(kafkaEmployee.getEmployeeId());
        if(kafkaEmployee==null){
            return StringConstant.INVALID_CREDENTIALS;
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
                return StringConstant.INVALID_CREDENTIALS;
            }
            cachingInfo.addEmployee(employee);
            salaryRepo.save(new Salary(kafkaEmployee.getEmployeeId(), kafkaEmployee.getSalary(), kafkaEmployee.getAccNo(), kafkaEmployee.getCompanyId(), kafkaEmployee.getDepartmentId()));
            kafkaTemplateEmployee.send(EMPLOYEE_INFORMATION_TOPIC, employee);
            return StringConstant.INFORMATION_SAVED_SUCCESSFULLY;
        }
        Company company = companyRepo.findById(kafkaEmployee.getCompanyId()).orElse(null);
        if (company == null) {
            return StringConstant.INVALID_CREDENTIALS;
        }
        try {
            Salary salary;
            salary = salaryRepo.getSalaryById(kafkaEmployee.getEmployeeId());
            salaryRepo.deleteById(salary.getId());
            salary.setSalary(kafkaEmployee.getSalary());
            List<Salary> list = new ArrayList<>();
            list.add(salary);
            cachingInfo.updateSalary(list, company.getId());
            kafkaTemplateSalary.send(SALARY_UPDATE_TOPIC,salary);
        }
        catch (Exception e){

        }

        return StringConstant.UPDATE_SUCCESSFUL;
    }

}
