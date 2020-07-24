package com.assignment.application.repo;

import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteCompInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

    @Query("Select comp from Company comp where UPPER(comp.name)=?1")
    Company getCompanyByName(String compName);

    @Query(value = "Select new com.assignment.application.entity.CompleteCompInfo(emp.name as employeeName,emp.employeeId as employeeId,emp.companyId as companyId,dept.name as departmentName,emp.departmentId as departmentId,sal.salary as salary,sal.accountNo as accountNo, " +
            "emp.projectId as projectId,emp.phoneNumber as phoneNumber,emp.currentAdd as currentAdd,emp.permanentAdd as permanentAdd) from Salary sal join sal.employee emp join emp.department dept where emp.companyId=?1")
    List<CompleteCompInfo> getCompanyCompleteInfo(Long companyId);

}
