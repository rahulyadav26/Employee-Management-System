package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Salary;
import com.assignment.application.update.SalaryEmployeeUpdate;
import com.assignment.application.update.SalaryUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface SalaryService {

    Salary addSalary(Long companyId, String employeeId, Salary salary, String userId);

    Page<Salary> getSalary(Long companyId, String employeeId, Pageable pageable);

    Page<Salary> getSalaryList(Pageable pageable);

    String updateSalary(Long companyId, SalaryUpdate salaryUpdate, String userId);

    String updateSalaryOfEmployee(Long companyId, String employeeId, SalaryEmployeeUpdate salaryEmployeeUpdate,
                                  String userId);

}
