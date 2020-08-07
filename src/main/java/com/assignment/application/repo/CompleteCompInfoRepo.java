package com.assignment.application.repo;

import org.springframework.stereotype.Component;

@Component
public interface CompleteCompInfoRepo {

    //    @Query(value = "Select mp.name as employeeName,emp.employeeId as employeeId,comp.id as companyId,deptList.departmentName as departmentName," +
    //                   "emp.departmentId as departmentId,sal.salary as salary,emp.phoneNumber as phoneNumber,emp.currentAddress as currentAddress,emp.permanentAddress as permanentAddress " +
    //                   "from Salary sal inner join Employee emp on emp.employeeId=sal.employeeId inner join Department dept on dept.id = emp.departmentId inner join DepartmentList deptList inner join" +
    //                   " on deptList.id = dept.departmentId inner join Company comp on dept.companyId = comp.id where dept.companyId=?1 and dept.isActive=1 " +
    //                   "and emp.isActive=1 and sal.updatedAt is null and sal.salary = (Select sal.salary from sal group by employeeId  order by sal.createdAt desc limit 1)",nativeQuery = true)
    //    Page<CompleteInfo> getCompanyCompleteInfo(Long companyId, Pageable pageable);

}
