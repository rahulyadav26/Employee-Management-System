package com.assignment.application.repo;

import com.assignment.application.entity.Company;
import com.assignment.application.entity.CompleteInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Long> {

    @Query("Select comp from Company comp where UPPER(comp.name)=?1")
    Company getCompanyByName(String compName);

    @Query("Select comp from Company comp where comp.isActive=1")
    List<Company> findAll();

    @Query(value = "Select new com.assignment.application.entity.CompleteInfo(emp.name as employeeName,emp.employeeId as employeeId,comp.id as companyId,deptList.departmentName as departmentName," +
                   "emp.departmentId as departmentId,sal.salary as salary,emp.phoneNumber as phoneNumber,emp.currentAddress as currentAddress,emp.permanentAddress as permanentAddress) " +
                   "from Salary sal join sal.employee emp join emp.department dept join dept.departmentList deptList join dept.company comp where dept.companyId=?1 and dept.isActive=1 " +
                   "and emp.isActive=1 and sal.isCurrent=1")
    Page<CompleteInfo> getCompanyCompleteInfo(Long companyId, Pageable pageable);

}
