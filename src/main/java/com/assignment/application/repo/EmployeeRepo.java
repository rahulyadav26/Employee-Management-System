package com.assignment.application.repo;

import com.assignment.application.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(value = "Select emp from Employee emp where emp.companyId = ?1", nativeQuery = true)
    List<Employee> getAllEmpByCompId(Long companyId);

    @Query(value = "Select emp from Employee emp where emp.employeeId = ?1")
    Employee getEmployee(String employeeId);

    @Query(value = "Select emp from Employee emp where emp.dob=?1 and UPPER(emp.name)=?2 and emp.phoneNumber=?3")
    Employee getEmployee(String dob, String name, String phoneNumber);

    @Query(value = "Select emp from Employee emp where emp.departmentId IN :list")
    Page<Employee> getEmployeesOfCompany(List<Long> list, Pageable pageable);

}
