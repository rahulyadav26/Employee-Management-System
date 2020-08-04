package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.DepartmentList;
import com.assignment.application.exception.DuplicateDataException;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.DepartmentListRepo;
import com.assignment.application.service.interfaces.DepartmentListService;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DepartmentListServiceImpl implements DepartmentListService {

    @Autowired
    private DepartmentListRepo departmentListRepo;

    @Override
    public DepartmentList addDepartment(DepartmentList departmentList) {
        if (departmentList == null || departmentList.getDepartmentName() == null ||
            departmentList.getDepartmentName().isEmpty()) {
            throw new InsufficientInformationException("Either request body is null or department name is missing");
        }
        DepartmentList checkDepartment =
                departmentListRepo.findDepartmentByName(departmentList.getDepartmentName().toUpperCase());
        if (checkDepartment != null) {
            throw new DuplicateDataException("Department already exists in database");
        }
        departmentListRepo.save(departmentList);
        return departmentList;
    }

    @Override
    public Page<DepartmentList> getDepartments(Pageable pageable) {
        return departmentListRepo.findAll(pageable);
    }

    @Override
    public String updateDepartment(Long departmentId, DepartmentInfoUpdate departmentInfoUpdate) {
        DepartmentList departmentList = departmentListRepo.findById(departmentId).orElse(null);
        if (departmentList == null) {
            throw new NotExistsException("No such department exists");
        }
        if (departmentInfoUpdate == null ||
            departmentInfoUpdate.getDepartmentName() == null ||
            departmentInfoUpdate.getDepartmentName().isEmpty()) {
            throw new InsufficientInformationException("Either request body is null or department name is empty/null");
        }
        departmentList.setDepartmentName(departmentInfoUpdate.getDepartmentName());
        departmentList.setUpdatedAt(new Date());
        departmentList.setUpdatedBy("0");
        departmentListRepo.save(departmentList);
        return StringConstant.UPDATE_SUCCESSFUL;
    }

}
