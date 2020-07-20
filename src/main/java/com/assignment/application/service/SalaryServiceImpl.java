package com.assignment.application.service;

import com.assignment.application.service.interfaces.SalaryServiceInterface;
import com.assignment.application.entity.Department;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.update.SalaryUpdate;
import com.assignment.application.entity.Salary;
import com.assignment.application.repo.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalaryServiceImpl implements SalaryServiceInterface {

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public ResponseEntity<Salary> addSalary(Long companyID, String employeeID, Salary salary) {
        try{
            if(!salary.getEmployeeId().equals(employeeID) || salary.getCompanyId()!=companyID ||salary.getId()==0){
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(salaryRepo.save(salary),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Salary> getSalary(Long companyId, String employeeId) {
        try{
            Salary salary = salaryRepo.getSalaryById(employeeId);
            return new ResponseEntity<>(salary,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Salary>> getSalaryList() {
        try{
            List<Salary> salaryList = salaryRepo.findAll();
            return new ResponseEntity<>(salaryList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateSalary(Long companyId, SalaryUpdate salaryUpdate) {
        try{
            if(salaryUpdate.getType().equals("0")){
                //whole company
                List<Salary> salaryList = salaryRepo.salaryListComp(companyId);
                if(salaryUpdate.getSubType().equals("0")){
                    //update by amount
                    for(int i=0;i<salaryList.size();i++){
                        salaryList.get(i).setSalary(salaryList.get(i).getSalary()+salaryUpdate.getValue());
                    }
                }
                else{
                    //update by percentage
                    for(int i=0;i<salaryList.size();i++){
                        double val = (salaryUpdate.getValue()/(double)100)*salaryList.get(i).getSalary();
                        salaryList.get(i).setSalary(salaryList.get(i).getSalary()+val);
                    }
                }
                salaryRepo.saveAll(salaryList);
            }
            else{
                //dept of a company
                Department department = departmentRepo.getDeptByCompId(companyId,salaryUpdate.getDept_name());
                List<Salary> salaryList = salaryRepo.salaryListCompDept(companyId,department.getId());
                if(salaryUpdate.getSubType().equals("0")){
                    //update by amount
                    for(int i=0;i<salaryList.size();i++){
                        salaryList.get(i).setSalary(salaryList.get(i).getSalary()+salaryUpdate.getValue());
                    }
                }
                else{
                    //update by percentage
                    for(int i=0;i<salaryList.size();i++){
                        double val = (salaryUpdate.getValue()/(double)100)*salaryList.get(i).getSalary();
                        salaryList.get(i).setSalary(salaryList.get(i).getSalary()+val);
                    }
                }
                salaryRepo.saveAll(salaryList);
            }
            return new ResponseEntity<>("Salary Updated",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
