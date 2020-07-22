package com.assignment.application.service;

import com.assignment.application.Constants.StringConstants;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.interfaces.DepartmentServiceI;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentServiceImpl implements DepartmentServiceI {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private StringConstants stringConstants;

    @Override
    public Department addDepartment(Long companyId, Department department) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company==null || !companyId.equals(department.getCompanyId()) || department.getId().equals(0)) {
            return null;
        }
        return departmentRepo.save(department);

    }

    @Override
    public List<Department> getDepartments() {
        List<Department> departmentList = departmentRepo.findAll();
        return departmentList;
    }

    @Override
    public String updateDepartmentInfo(Long companyId, Long id, DepartmentInfoUpdate departmentInfoUpdate) {
        Department department = departmentRepo.findById(id).orElse(null);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company==null || !department.getCompanyId().equals(companyId) || departmentInfoUpdate == null) {
            return stringConstants.invalidStatus;
        }
        if (!departmentInfoUpdate.getHead().isEmpty()) {
            department.setHead(departmentInfoUpdate.getHead());
        }
        if (!departmentInfoUpdate.getEmployeeCount().isEmpty()) {
            department.setEmployeeCount(Long.parseLong(departmentInfoUpdate.getEmployeeCount()));
        }
        if (!departmentInfoUpdate.getOngoingProject().isEmpty()) {
            department.setOngoingProject(Long.parseLong(departmentInfoUpdate.getOngoingProject()));
        }
        if (!departmentInfoUpdate.getCompletedProject().isEmpty()) {
            department.setCompletedProject(Long.parseLong(departmentInfoUpdate.getCompletedProject()));
        }
        departmentRepo.save(department);
        return stringConstants.updateStatus;
    }

    @Override
    public Department getDepartment(Long companyId, Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company==null || !department.getCompanyId().equals(companyId)) {
            return null;
        }
        return department;
    }

    @Override
    public String deleteDepartmentOfCompany(Long id, Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Department department = departmentRepo.findById(id).orElse(null);
        if(company==null || department==null || !department.getCompanyId().equals(company.getId())){
            return stringConstants.invalidStatus;
        }
        departmentRepo.deleteById(id);
        return stringConstants.deleteStatus;
    }
}
