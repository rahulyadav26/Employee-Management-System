package com.assignment.application.service;

import com.assignment.application.entity.Family;
import com.assignment.application.repo.FamilyRepo;
import com.assignment.application.service.interfaces.FamilyServiceInterface;
import com.assignment.application.update.FamilyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FamilyServiceImpl implements FamilyServiceInterface {

    @Autowired
    FamilyRepo familyRepo;

    @Autowired
    Family family;

    @Override
    public ResponseEntity<Family> addMember(String empId, Family family) {
        try{
            System.out.println(empId + " " + family.getEmp_id());
            if(!empId.equals(family.getEmp_id())){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            family.setName(family.getName().toLowerCase());
            family.setOccupation(family.getOccupation().toLowerCase());
            this.family = familyRepo.save(family);
            return new ResponseEntity<>(family,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Family>> getMember() {
        try{
            List<Family> familyList = familyRepo.findAll();
            return new ResponseEntity<>(familyList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Family>> getMemberofEmp(String empId) {
        try{
            List<Family> familyList = familyRepo.getListById(empId);
            return new ResponseEntity<>(familyList,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateInfo(String empId, String name, FamilyInfoUpdate familyInfoUpdate) {
        try{
            family = familyRepo.getFamInfo(empId,name);
            if(family==null){
                return new ResponseEntity<>("No data exists",HttpStatus.OK);
            }
            if(familyInfoUpdate.getType().equals("0")){
                family.setAddress(familyInfoUpdate.getAddress());
            }
            else if(familyInfoUpdate.getType().equals("1")){
                family.setPhone_number(familyInfoUpdate.getPhone_number());
            }
            else{
                family.setAddress(familyInfoUpdate.getAddress());
                family.setPhone_number(familyInfoUpdate.getPhone_number());
            }
            familyRepo.save(family);
            return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteMember(String empId,String name) {
        try {
            family = familyRepo.getFamInfo(empId, name);
            if (family == null) {
                return new ResponseEntity<>("No data exists",HttpStatus.OK);
            }
            familyRepo.delete(family);
            return new ResponseEntity<>("Deletion Successful",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
