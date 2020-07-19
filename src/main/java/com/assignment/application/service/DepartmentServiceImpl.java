package com.assignment.application.service;

import com.assignment.application.entity.Department;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.interfaces.DepartmentServiceInterface;
import com.assignment.application.update.department.CompletedProjectUpdate;
import com.assignment.application.update.department.DeptHeadUpdate;
import com.assignment.application.update.department.EmpCountUpdate;
import com.assignment.application.update.department.OngoingProjectUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentServiceImpl implements DepartmentServiceInterface {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    Department department;

    @Override
    public ResponseEntity<Department> addDepartment(long companyId,Department department) {
        try{
            List<Department> departmentList = departmentRepo.findAll();
            if(companyId!=department.getComp_id()){
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            this.department.setComp_id(department.getComp_id());
            this.department.setCompleted_project(department.getCompleted_project());
            this.department.setEmployee_count(department.getEmployee_count());
            this.department.setHead(department.getHead());
            this.department.setId(department.getId());
            this.department.setName(department.getName());
            this.department.setOngoing_project(department.getOngoing_project());
            return new ResponseEntity<>(departmentRepo.save(this.department),HttpStatus.OK);
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
    public ResponseEntity<String> updateDeptHead(long companyId,long id,DeptHeadUpdate deptHeadUpdate) {
        try{
            department = departmentRepo.findById(id).orElse(null);
            if(department==null || department.getComp_id()!=companyId){
                return new ResponseEntity<>("No such department exists",HttpStatus.OK);
            }
            department.setHead(deptHeadUpdate.getName());
            departmentRepo.save(department);
            return new ResponseEntity<>("Update Successful",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateDeptEmpCount(long companyId,long id, EmpCountUpdate empCountUpdate) {
        try{
            department = departmentRepo.findById(id).orElse(null);
            if(department==null || companyId!=department.getComp_id()){
                return new ResponseEntity<>("No such department exists",HttpStatus.OK);
            }
            department.setEmployee_count(empCountUpdate.getEmp_count());
            departmentRepo.save(department);
            return new ResponseEntity<>("Update Successful",HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateDeptOngoingProj(long companyId,long id, OngoingProjectUpdate ongoingProjectUpdate) {
        try{
            department = departmentRepo.findById(id).orElse(null);
            if(department==null || companyId!=department.getComp_id()){
                return new ResponseEntity<>("No such department Exists",HttpStatus.OK);
            }
            department.setOngoing_project(ongoingProjectUpdate.getOngoing_project());
            departmentRepo.save(department);
            return new ResponseEntity<>("Update Successful",HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateDeptCompletedProj(long companyId,long id, CompletedProjectUpdate completedProjectUpdate) {
        try{
            department = departmentRepo.findById(id).orElse(null);
            if(department==null || companyId!=department.getComp_id()){
                return new ResponseEntity<>("No such department Exists",HttpStatus.OK);
            }
            long count = completedProjectUpdate.getCompleted_project()-department.getCompleted_project();
            department.setOngoing_project(department.getOngoing_project()-count);
            department.setCompleted_project(completedProjectUpdate.getCompleted_project());
            departmentRepo.save(department);
            return new ResponseEntity<>("Update Successful",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while Updating",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteDepartment(long companyId,long id) {
        try{
            department = departmentRepo.findById(id).orElse(null);
            if(department==null || companyId!=department.getComp_id()){
                return new ResponseEntity<>("No such department Exists",HttpStatus.OK);
            }
            departmentRepo.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while deleting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Department> getDepartment(long companyId,long id) {
        try{
            department = departmentRepo.findById(id).orElse(null);
            if(department==null || department.getComp_id()!=companyId){
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
