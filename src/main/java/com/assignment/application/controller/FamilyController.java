package com.assignment.application.controller;

import com.assignment.application.entity.Family;
import com.assignment.application.service.interfaces.FamilyServiceInterface;
import com.assignment.application.update.FamilyInfoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FamilyController {

    @Autowired
    FamilyServiceInterface familyServiceInterface;

    @RequestMapping(value = "/{emp_id}/family" , method = RequestMethod.POST)
    public ResponseEntity<Family> addMember(@PathVariable("emp_id") String empId, @RequestBody Family family){
        return familyServiceInterface.addMember(empId,family);
    }
    @RequestMapping(value = "/family" , method = RequestMethod.GET)
    public ResponseEntity<List<Family>> getMember(){
        return familyServiceInterface.getMember();
    }

    @RequestMapping(value = "/{emp_id}/family" , method = RequestMethod.GET)
    public ResponseEntity<List<Family>> getMemberofEmp(@PathVariable("emp_id") String empId){
        return familyServiceInterface.getMemberofEmp(empId);
    }

    @RequestMapping(value = "/{emp_id}/family/{member_name}/update-info" , method = RequestMethod.PATCH)
    public ResponseEntity<String> updateInfo(@PathVariable("emp_id")String empId,
                                             @PathVariable("member_name")String name,
                                             @RequestBody FamilyInfoUpdate familyInfoUpdate) {
        return familyServiceInterface.updateInfo(empId,name,familyInfoUpdate);
    }

    @RequestMapping(value = "{emp_id}/family/{member_name}" , method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMember(@PathVariable("emp_id") String empId,
                                               @PathVariable("member_name") String name){
        return familyServiceInterface.deleteMember(empId,name);
    }

}
