package com.assignment.application.service;

import com.assignment.application.entity.CompleteCompInfo;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.CompleteCompInfoRepo;
import com.sun.org.apache.xerces.internal.impl.io.UCSReader;
import org.hibernate.Criteria;
import org.hibernate.Metamodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompleteCompInfoImpl implements CompleteCompInfoRepo {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<CompleteCompInfo> companyCompleteList(Long companyId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CompleteCompInfo> criteriaQuery = criteriaBuilder.createQuery(CompleteCompInfo.class);
//        Metamodel metamodel = entityManager.getMetamodel();
//
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Root<Salary> salaryRoot = criteriaQuery.from(Salary.class);
        Root<Department> departmentRoot  = criteriaQuery.from(Department.class);
        Join<Employee,Salary> salaryEmployeeJoin = salaryRoot.join("employee",JoinType.INNER);
        Join<Department,Employee> employeeDepartmentJoin = salaryEmployeeJoin.join("department",JoinType.INNER);
        List<Predicate> predicate = new ArrayList<>();
//        predicate.add(criteriaBuilder.equal(employeeDepartmentJoin.get("companyId"),companyId));
//        predicate.add(criteriaBuilder.equal(salaryEmployeeJoin.get("companyId"),companyId));
        predicate.add(criteriaBuilder.equal(employeeRoot.get("companyId"),companyId));
        TypedQuery<CompleteCompInfo> typedQuery = entityManager.createQuery(criteriaQuery.multiselect(employeeRoot.get("name"),employeeRoot.get("employeeId"),
                employeeRoot.get("companyId"),departmentRoot.get("name"),departmentRoot.get("id"),salaryRoot.get("salary"),salaryRoot.get("accountNo"),employeeRoot.get("projectId"),
                employeeRoot.get("phoneNumber"),employeeRoot.get("currentAdd"),employeeRoot.get("permanentAdd"))
                .distinct(true)
                .where(predicate.toArray(new Predicate[]{})));
        List<CompleteCompInfo> completeCompInfos = typedQuery.getResultList();
        return completeCompInfos.stream().distinct().collect(Collectors.toList());
    }
}
