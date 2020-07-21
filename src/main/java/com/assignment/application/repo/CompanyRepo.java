package com.assignment.application.repo;

import com.assignment.application.entity.Company;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company,Long> {

    @Query("Select comp from Company comp where comp.id=?1")
    Company getCompany(Long compId);

    @Query("Select comp from Company comp where UPPER(comp.name)=?1")
    Company getCompanyByName(String compName);

    @Query(value = "Select emp.name as emp_name,emp.emp_id as emp_id,emp.comp_id as comp_id,dept.name as dept_name,emp.dept_id as dept_id,sal.salary as salary,sal.acc_no as acc_no," +
            " emp.project_id as project_id,emp.phone_number as phone_number,emp.current_add as current_add,emp.permanent_add as permanent_add from Employee emp" +
            " inner join Department dept on emp.dept_id = dept.id inner join Salary sal on sal.emp_id=emp.emp_id" +
            " where emp.comp_id=?1",nativeQuery = true)
    List<Object> getCompDataSet(Long compId);
//

}
