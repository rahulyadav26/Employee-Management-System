//package com.assignment.application.service;
//
//import com.assignment.application.entity.CompleteCompInfo;
//import com.assignment.application.entity.Department;
//import com.assignment.application.entity.Employee;
//import com.assignment.application.entity.Salary;
//import com.assignment.application.repo.CompleteCompInfoRepo;
//import com.sun.org.apache.xerces.internal.impl.io.UCSReader;
//import org.hibernate.Criteria;
//import org.hibernate.Metamodel;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class CompleteCompInfoImpl implements CompleteCompInfoRepo {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public List<CompleteCompInfo> companyCompleteList(Long companyId) {
//        SessionFactory sessionFactory = new Configuration().buildSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//        Criteria criteria = session.createCriteria(Salary.class,"salary");
//        criteria.createAlias("salary.employee","employee");
//        criteria.createAlias("employee.department","department");
//        criteria.add(Restrictions.eq("salary.employeeId","employee.employeeId"));
//        criteria.add(Restrictions.eq("employee.departmentId","department.Id"));
//        List<CompleteCompInfo> list = criteria.list();
////        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
////        CriteriaQuery<CompleteCompInfo> criteriaQuery = criteriaBuilder.createQuery(CompleteCompInfo.class);
////        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
////        Root<Salary> salaryRoot = criteriaQuery.from(Salary.class);
////        Root<Department> departmentRoot  = criteriaQuery.from(Department.class);
////
////        Join<Employee,Salary> salaryEmployeeJoin = salaryRoot.join("employee",JoinType.INNER);
////        Join<Employee,Department> employeeDepartmentJoin = salaryEmployeeJoin.join("department",JoinType.INNER);
////        List<Predicate> predicate = new ArrayList<>();
//////        predicate.add(criteriaBuilder.equal(employeeDepartmentJoin.get("companyId"),companyId));
//////        predicate.add(criteriaBuilder.equal(salaryEmployeeJoin.get("companyId"),companyId));
////        predicate.add(criteriaBuilder.equal(employeeRoot.get("companyId"),companyId));
////        TypedQuery<CompleteCompInfo> typedQuery = entityManager.createQuery(criteriaQuery.multiselect(employeeRoot.get("name"),employeeRoot.get("employeeId"),
////                employeeRoot.get("companyId"),employeeDepartmentJoin.get("name"),employeeDepartmentJoin.get("id"),salaryRoot.get("salary"),salaryRoot.get("accountNo"),employeeRoot.get("projectId"),
////                employeeRoot.get("phoneNumber"),employeeRoot.get("currentAdd"),employeeRoot.get("permanentAdd"))
////                .where(predicate.toArray(new Predicate[]{})));
////        List<CompleteCompInfo> completeCompInfos = typedQuery.getResultList();
//        return list;
//    }
//}
