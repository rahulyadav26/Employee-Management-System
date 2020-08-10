package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.repo.SalaryRepo;
import com.assignment.application.service.interfaces.SalaryService;
import com.assignment.application.update.SalaryEmployeeUpdate;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private KafkaTemplate<String, SalaryUpdate> kafkaTemplateSalary;

    @Override
    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyID"),
                      @CacheEvict(value = "companyCompleteInfo", key = "#companyID")})
    public Salary addSalary(Long companyID, String employeeID, Salary salary, String userId) {
        Company company = companyRepo.findById(companyID).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeID);
        if (companyID != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_EMPLOYEE_EXISTS);
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (companyID != 0 &&
            (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyID))) {
            throw new NotExistsException(
                    "Either department for employee is not valid or employee doesn't belong to the specified company");
        }
        if (salary == null || salary.getSalary() == null || salary.getSalary()<=0) {
            throw new InsufficientInformationException("Insufficient data found in request body");
        }
        Salary checkSalary = salaryRepo.getCurrentSalaryById(employeeID);
        if (checkSalary != null) {
            throw new DuplicateDataException("Employee salary exists you can update the salary using update url");
        }
        salary.setCreatedBy(userId);
        salary.setEmployeeId(employeeID);
        salary.setCreatedAt(new Date());
        return salaryRepo.save(salary);
    }

    @Override
    public Page<Salary> getSalary(Long companyId, String employeeId, Pageable pageable) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeId);
        Page<Salary> salary = salaryRepo.getSalaryByEmployeeId(employeeId, pageable);
        if (companyId != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException("Company not exists in database");
        }
        if (employee == null || employee.getIsActive() == 0 || salary.getTotalElements() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_EMPLOYEE_EXISTS);
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (companyId != 0 && (department == null || department.getIsActive() == 0)) {
            throw new NotExistsException("Department not exists");
        }
        if (companyId != 0 && !department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException("Company Id not valid for this employee");
        }
        return salary;
    }

    @Override
    public Page<Salary> getSalaryList(Pageable pageable) {
        Page<Salary> salaryList = salaryRepo.findAll(pageable);
        return salaryList;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"),
                      @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String updateSalary(Long companyId, SalaryUpdate salaryUpdate, String userId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        if (salaryUpdate == null || salaryUpdate.getValue() == null || salaryUpdate.getType() == null ||
            salaryUpdate.getType().isEmpty() || salaryUpdate.getSubType() == null ||
            salaryUpdate.getSubType().isEmpty() || salaryUpdate.getValue() == null || salaryUpdate.getValue() <= 0L) {
            throw new EmptyUpdateException("Salary Update info is not valid");
        }
        if (salaryUpdate.getType().equals("1") && salaryUpdate.getDepartmentId() == null) {
            throw new EmptyUpdateException("Please provide the department id");
        }
        if (salaryUpdate.getType().equals("0")) {
            return updateCompanySalary(companyId, salaryUpdate, userId);

        } else if (salaryUpdate.getType().equals("1")) {
            return updateDepartmentSalary(companyId, salaryUpdate, userId);

        }
        throw new InsufficientInformationException("Type not valid");
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "companyEmployeeList", key = "#companyId"),
                      @CacheEvict(value = "companyCompleteInfo", key = "#companyId")})
    public String updateSalaryOfEmployee(Long companyId, String employeeId, SalaryEmployeeUpdate salaryEmployeeUpdate,
                                         String userId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Employee employee = employeeRepo.getEmployee(employeeId);
        if (companyId != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_EXISTS);
        }
        if (employee == null || employee.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_EMPLOYEE_EXISTS);
        }
        if (salaryEmployeeUpdate == null || salaryEmployeeUpdate.getValue() == null ||
            salaryEmployeeUpdate.getValue() <= 0L) {
            throw new InsufficientInformationException("Insufficient data found in request body");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (companyId != 0 &&
            (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyId))) {
            throw new NotExistsException("Company id is not valid for the employee");
        }
        List<Salary> salary = salaryRepo.getSalaryByEmployeeId(employeeId);
        if (salary.isEmpty()) {
            throw new NotExistsException("No entry found in database");
        }
        Salary currentSalary = salaryRepo.getCurrentSalaryById(employeeId);
        if (currentSalary != null) {
            currentSalary.setUpdatedAt(new Date());
            currentSalary.setUpdatedBy(userId);
            salaryEmployeeUpdate.setValue(currentSalary.getSalary() + salaryEmployeeUpdate.getValue());
            salaryRepo.save(currentSalary);
        }
        Salary salaryToBeUpdated = new Salary();
        salaryToBeUpdated.setSalary(salaryEmployeeUpdate.getValue());
        salaryToBeUpdated.setUpdatedBy(null);
        salaryToBeUpdated.setUpdatedAt(null);
        salaryToBeUpdated.setCreatedBy(userId);
        salaryToBeUpdated.setCreatedAt(new Date());
        salaryToBeUpdated.setEmployeeId(employeeId);
        salaryRepo.save(salaryToBeUpdated);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    public String updateCompanySalary(Long companyId, SalaryUpdate salaryUpdate, String userId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        List<Long> departments = departmentRepo.getDepartmentOfCompany(companyId);
        if (departments.size() == 0) {
            throw new NotExistsException("No department of the company found");
        }
        List<Employee> employeeList = employeeRepo.getEmployeesOfCompany(departments);
        return updateSalary(companyId, salaryUpdate, employeeList, userId);
    }

    public String updateDepartmentSalary(Long companyId, SalaryUpdate salaryUpdate, String userId) {
        Department department = departmentRepo.findById(salaryUpdate.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive() == 0 || !department.getCompanyId().equals(companyId)) {
            throw new NotExistsException("No such department exists");
        }
        List<Long> departmentList = new ArrayList<>();
        departmentList.add(department.getId());
        List<Employee> employeeList = employeeRepo.getEmployeesOfCompany(departmentList);
        return updateSalary(companyId, salaryUpdate, employeeList, userId);
    }

    public String updateSalary(Long companyId, SalaryUpdate salaryUpdate, List<Employee> employeeList, String userId) {
        if (salaryUpdate.getSubType().equals("0")) {
            updateByValue(companyId, salaryUpdate, employeeList, userId);

        } else if (salaryUpdate.getSubType().equals("1")) {
            updateByPercentage(companyId, salaryUpdate, employeeList, userId);
        } else {
            throw new InsufficientInformationException("Subtype not valid");
        }
        kafkaTemplateSalary.send(StringConstant.SALARY_UPDATE_TOPIC, salaryUpdate);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    public void updateByPercentage(Long companyId, SalaryUpdate salaryUpdate, List<Employee> employeeList,
                                   String userId) {
        for (int i = 0; i < employeeList.size(); i++) {
            Salary salary = salaryRepo.getCurrentSalaryById(employeeList.get(i).getEmployeeId());
            List<Salary> salaryList = salaryRepo.getSalaryByEmployeeId(employeeList.get(i).getEmployeeId());
            double val = 0;
            if (salary != null && !salaryList.isEmpty()) {
                val = ((salaryUpdate.getValue()) / (double) 100) * salary.getSalary();
                updateSalaryOfEmployee(companyId, employeeList.get(i).getEmployeeId(),
                                       new SalaryEmployeeUpdate(val), userId);
            }
        }
    }

    public void updateByValue(Long companyId, SalaryUpdate salaryUpdate, List<Employee> employeeList, String userId) {
        for (int i = 0; i < employeeList.size(); i++) {
            Salary salary = salaryRepo.getCurrentSalaryById(employeeList.get(i).getEmployeeId());
            List<Salary> salaryList = salaryRepo.getSalaryByEmployeeId(employeeList.get(i).getEmployeeId());
            if (salary != null && !salaryList.isEmpty()) {
                updateSalaryOfEmployee(companyId, employeeList.get(i).getEmployeeId(),
                                       new SalaryEmployeeUpdate((double) salaryUpdate.getValue()), userId);
            }
        }
    }

}
