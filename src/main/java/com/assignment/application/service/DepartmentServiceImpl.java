package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.exception.DuplicateDataException;
import com.assignment.application.exception.EmptyDatabaseException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.interfaces.DepartmentServiceI;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentServiceImpl implements DepartmentServiceI {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private StringConstant stringConstant;

    @Override
    public Department addDepartment(Long companyId, Department department) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company==null || department == null || department.getName().isEmpty() || !companyId.equals(department.getCompanyId())) {
            throw new RuntimeException("Data Invalid");
        }
        if(departmentRepo.getDeptByCompId(companyId,department.getName().toUpperCase())!=null){
            throw new DuplicateDataException("Data Already Exists");
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
        if (department == null || company==null || !department.getCompanyId().equals(companyId)){
            throw new RuntimeException("Data not valid");
        }
        if(departmentInfoUpdate==null){
            throw new EmptyUpdateException("Information is not valid");
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
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public Department getDepartment(Long companyId, Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company==null || !department.getCompanyId().equals(companyId)) {
            throw new RuntimeException("Data not Valid");
        }
        return department;
    }

    @Override
    public String deleteDepartmentOfCompany(Long departmentId, Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department==null || company==null || !department.getCompanyId().equals(company.getId())){
           throw new RuntimeException("Data not valid");
        }
        departmentRepo.deleteById(departmentId);
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
