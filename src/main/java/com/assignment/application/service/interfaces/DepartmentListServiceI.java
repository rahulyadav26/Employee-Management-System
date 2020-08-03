package com.assignment.application.service.interfaces;

import com.assignment.application.entity.DepartmentList;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface DepartmentListServiceI {

    DepartmentList addDepartment(DepartmentList departmentList);

    Page<DepartmentList> getDepartments(Pageable pageable);

    String updateDepartment(Long departmentId, DepartmentInfoUpdate departmentInfoUpdate);

}
