package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Family;
import com.assignment.application.update.FamilyInfoUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;

@Component
public interface FamilyServiceInterface {

    ResponseEntity<Family> addMember(String empId,Family family);

    ResponseEntity<List<Family>> getMember();

    ResponseEntity<List<Family>> getMemberofEmp(String empId);

    ResponseEntity<String> updateInfo(String empId, String name, FamilyInfoUpdate familyInfoUpdate);

    ResponseEntity<String> deleteMember(String empId,String name);

}
