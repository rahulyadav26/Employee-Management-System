package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.DepartmentList;
import com.assignment.application.exception.*;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentListRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.interfaces.DepartmentService;
import com.assignment.application.update.DepartmentUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private StringConstant stringConstant;

    @Autowired
    private CachingInfo cachingInfo;

    @Autowired
    private DepartmentListRepo departmentListRepo;

    @Override
    public Department addDepartment(Long companyId, Department department) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        if (department == null || department.getDepartmentId() == null) {
            throw new InsufficientInformationException("Not sufficient information found in the request");
        }
        DepartmentList departmentList = departmentListRepo.findById(department.getDepartmentId()).orElse(null);
        if (departmentList == null) {
            throw new NotExistsException("No such department exists");
        }
        Department checkDepartment = departmentRepo.getDepartmentOfCompany(companyId, department.getDepartmentId());
        if (checkDepartment != null && checkDepartment.getIsActive() == 1) {
            throw new DuplicateDataException("Department already exists in database");
        }
        if (checkDepartment != null) {
            checkDepartment.setIsActive(1L);
            checkDepartment.setUpdatedAt(new Date());
            checkDepartment.setUpdatedBy("0");
            return departmentRepo.save(checkDepartment);
        }
        department.setCompanyId(companyId);
        department.setCreatedBy("0");
        return departmentRepo.save(department);

    }

    @Override
    public Page<Department> getDepartments(Pageable pageable) {
        return departmentRepo.findAll(pageable);
    }

    @Override
    public Page<Department> getDepartmentsOfCompany(Long companyId, Pageable pageable) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null || company.getIsActive() == 0) {
            throw new NotExistsException("No such company exists");
        }
        return departmentRepo.getDepartmentOfCompany(companyId, pageable);
    }

    @Override
    public Department getDepartment(Long companyId, Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company == null || company.getIsActive() == 0 || department.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_OR_DEPARTMENT_EXISTS);
        }
        if (!department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException(StringConstant.COMPANY_ID_NOT_VALID_FOR_DEPARTMENT);
        }
        return department;
    }

    @Override
    public String updateDepartmentInfo(Long companyId, Long id, DepartmentUpdate departmentUpdate) {
        Department department = departmentRepo.findById(id).orElse(null);
        Company company = companyRepo.findById(companyId).orElse(null);
        if (department == null || company == null || company.getIsActive() == 0 || department.getIsActive() == 0) {
            throw new NotExistsException(StringConstant.NO_SUCH_COMPANY_OR_DEPARTMENT_EXISTS);
        }
        if (!department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException(StringConstant.COMPANY_ID_NOT_VALID_FOR_DEPARTMENT);
        }
        if (departmentUpdate == null || ((departmentUpdate.getHead() == null || departmentUpdate.getHead().isEmpty())
                                         && departmentUpdate.getDepartmentId() == null &&
                                         departmentUpdate.getCompanyId() == null)) {
            throw new EmptyUpdateException("Information is not valid");
        }
        if (departmentUpdate.getCompanyId() != null) {
            Company checkCompany = companyRepo.findById(departmentUpdate.getCompanyId()).orElse(null);
            if (checkCompany == null || checkCompany.getIsActive() == 0) {
                throw new NotExistsException("Company given in request body not exists");
            }
            department.setCompanyId(departmentUpdate.getCompanyId());
            department.setUpdatedAt(new Date());
            department.setUpdatedBy("0");
        }
        if (departmentUpdate.getDepartmentId() != null) {
            if (!departmentListRepo.existsById(department.getDepartmentId())) {
                throw new NotExistsException("Department given in request body not exists");
            }
            department.setCompanyId(departmentUpdate.getDepartmentId());
            department.setUpdatedAt(new Date());
            department.setUpdatedBy("0");
        }
        if (departmentUpdate.getHead() != null || !departmentUpdate.getHead().equals("")) {
            department.setHead(departmentUpdate.getHead());
            department.setUpdatedAt(new Date());
            department.setUpdatedBy("0");
        }
        departmentRepo.save(department);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteDepartmentOfCompany(Long departmentId, Long companyId) {
        Company company = companyRepo.findById(companyId).orElse(null);
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if (department == null || company == null || department.getIsActive() == 0 || company.getIsActive() == 0) {
            throw new NotExistsException("No such company/department exists");
        }
        if (!department.getCompanyId().equals(companyId)) {
            throw new DataMismatchException("Company id not valid for the department");
        }
        department.setIsActive(0L);
        department.setUpdatedAt(new Date());
        department.setUpdatedBy("0");
        departmentRepo.save(department);
        //cachingInfo.deleteDepartment(companyId,departmentId);
        return StringConstant.DELETION_SUCCESSFUL;
    }
}
