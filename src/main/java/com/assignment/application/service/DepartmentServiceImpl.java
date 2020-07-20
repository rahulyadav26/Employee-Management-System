package com.assignment.application.service;

import com.assignment.application.entity.Department;
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

    @Override
    public ResponseEntity<Department> addDepartment(Long companyId,Department department) {
        try{
            if(companyId!=department.getCompanyId() || department.getId()==0){
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(departmentRepo.save(department),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Department>> getDepartments() {
        try{
            List<Department> departmentList = departmentRepo.findAll();
            return new ResponseEntity<>(departmentList,HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateDepartmentInfo(Long companyId, Long id, DepartmentInfoUpdate departmentInfoUpdate) {
        try{
            Department department = departmentRepo.findById(id).orElse(null);
            if(department==null || department.getCompanyId()!=companyId){
                return new ResponseEntity<>("No such department exists",HttpStatus.OK);
            }
            if(!departmentInfoUpdate.getHead().isEmpty()){
                department.setHead(departmentInfoUpdate.getHead());
            }
            if(!departmentInfoUpdate.getEmployeeCount().isEmpty()){
                department.setEmployeeCount(Long.parseLong(departmentInfoUpdate.getEmployeeCount()));
            }
            if(!departmentInfoUpdate.getOngoingProject().isEmpty()){
                department.setOngoingProject(Long.parseLong(departmentInfoUpdate.getOngoingProject()));
            }
            if(!departmentInfoUpdate.getCompletedProject().isEmpty()){
                department.setCompletedProject(Long.parseLong(departmentInfoUpdate.getCompletedProject()));
            }
            departmentRepo.save(department);
            return new ResponseEntity<>("Update Successful",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Department> getDepartment(Long companyId,Long id) {
        try{
            Department department = departmentRepo.findById(id).orElse(null);
            if(department==null || department.getCompanyId()!=companyId){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            return new ResponseEntity<>(department,HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
