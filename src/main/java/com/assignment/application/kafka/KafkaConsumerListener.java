package com.assignment.application.kafka;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import com.assignment.application.entity.Salary;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.service.interfaces.EmployeeService;
import com.assignment.application.service.interfaces.SalaryService;
import com.assignment.application.update.SalaryEmployeeUpdate;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
    private SalaryService salaryService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    @Autowired
    private KafkaTemplate<String, Salary> kafkaTemplateSalary;

    @Autowired
    private ModelMapper modelMapper;

    @KafkaListener(topics = "employeeAddition", groupId = "employee",
                   containerFactory = "concurrentKafkaListenerContainerFactory")
    public String consumerEmployeeInfo(ConsumerRecord<String, Object> record) {
        KafkaEmployee kafkaEmployee = modelMapper.map(record.value(), KafkaEmployee.class);
        if (kafkaEmployee == null || kafkaEmployee.getSalary() == null) {
            throw new InsufficientInformationException("Body cannot be null");
        }
        if (kafkaEmployee.getEmployeeId() != null && !kafkaEmployee.getEmployeeId().isEmpty()) {
            String val = updateSalary(kafkaEmployee);
            if (!val.isEmpty()) {
                return StringConstant.UPDATE_SUCCESSFUL;
            }
        }
        if (kafkaEmployee == null || kafkaEmployee.getDob() == null ||
            kafkaEmployee.getName() == null || kafkaEmployee.getName().isEmpty() ||
            kafkaEmployee.getUniqueId() == null
            || kafkaEmployee.getUniqueId().isEmpty()) {
            throw new InsufficientInformationException("Insufficient data found in request");
        }
        if (kafkaEmployee.getRoleName() == null || kafkaEmployee.getRoleName().isEmpty() ||
            kafkaEmployee.getEmployeeType() == null || kafkaEmployee.getEmployeeType().isEmpty()) {
            throw new InsufficientInformationException("Either the role name or employee type is missing");
        }
        Employee employee = modelMapper.map(kafkaEmployee, Employee.class);
        employee = addEmployee(employee);
        if(employee==null){
            return StringConstant.INVALID_CREDENTIALS;
        }
        kafkaEmployee.setEmployeeId(employee.getEmployeeId());
        return addSalary(kafkaEmployee);
    }

    public String updateSalary(KafkaEmployee kafkaEmployee) {
        Employee employee = employeeRepo.getEmployee(kafkaEmployee.getEmployeeId());
        if (employee != null && employee.getIsActive() != 0) {
            if (kafkaEmployee.getDepartmentId() == null || kafkaEmployee.getDepartmentId() == 0) {
                return salaryService.updateSalaryOfEmployee(0L, kafkaEmployee.getEmployeeId(),
                                                            new SalaryEmployeeUpdate(kafkaEmployee.getSalary()), "0");
            }
            Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
            if (department == null || department.getIsActive() == 0) {
                throw new NotExistsException("No such department is active");
            }
            return salaryService.updateSalaryOfEmployee(department.getCompanyId(), employee.getEmployeeId(),
                                                        new SalaryEmployeeUpdate(kafkaEmployee.getSalary()), "0");
        }
        return "";

    }

    public Employee addEmployee(Employee employee) {
        if (employee.getDepartmentId() == null || employee.getDepartmentId() == 0) {
            return employeeService.addEmployee(0L, employee, "0");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0) {
            throw new NotExistsException("No such department is active");
        }
        return employeeService.addEmployee(department.getCompanyId(), employee, "0");

    }

    public String addSalary(KafkaEmployee kafkaEmployee) {
        Salary salary = new Salary();
        salary.setSalary(kafkaEmployee.getSalary());
        if (kafkaEmployee.getDepartmentId() == null || kafkaEmployee.getDepartmentId() == 0) {
            salary = salaryService.addSalary(0L, kafkaEmployee.getEmployeeId(), salary, "0");
            if (salary == null) {
                return StringConstant.INVALID_CREDENTIALS;
            }
            return StringConstant.UPDATE_SUCCESSFUL;
        }
        Department department = departmentRepo.findById(kafkaEmployee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0) {
            throw new NotExistsException("No such department is active");
        }
        salary = salaryService.addSalary(department.getCompanyId(), kafkaEmployee.getEmployeeId(), salary, "0");
        if (salary == null) {
            return StringConstant.INVALID_CREDENTIALS;
        }
        return StringConstant.UPDATE_SUCCESSFUL;
    }
}
