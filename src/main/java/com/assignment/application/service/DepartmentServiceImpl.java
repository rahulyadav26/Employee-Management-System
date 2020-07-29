package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.interfaces.DepartmentServiceI;
import com.assignment.application.update.DepartmentInfoUpdate;
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
        if(company==null){
            throw new NotExistsException("No such company exists");
        }
        if (department == null || department.getName().isEmpty()){
            throw new InsufficientInformationException("Not sufficient information found in the request");
        }
        if(!department.getCompanyId().equals(companyId)){
            throw new DataMismatchException("Company id not valid for the department");
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
        if (department == null || company==null){
            throw new NotExistsException("No such company/department exists");
        }
        if(!department.getCompanyId().equals(companyId)){
            throw new DataMismatchException("Company id not valid for the department");
        }
        if(departmentInfoUpdate==null){
            throw new EmptyUpdateException("Information is not valid");
        }
        if (!departmentInfoUpdate.getHead().isEmpty()) {
            department.setHead(departmentInfoUpdate.getHead());
        }
        departmentRepo.save(department);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public Department getDepartment(Long companyId, Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company==null){
            throw new NotExistsException("No such company/department exists");
        }
        if(!department.getCompanyId().equals(companyId)){
            throw new DataMismatchException("Company id not valid for the department");
        }
        return department;
    }

    @Override
    public String deleteDepartmentOfCompany(Long departmentId, Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if (department == null || company==null){
            throw new NotExistsException("No such company/department exists");
        }
        if(!department.getCompanyId().equals(companyId)){
            throw new DataMismatchException("Company id not valid for the department");
        }
        departmentRepo.deleteById(departmentId);
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
