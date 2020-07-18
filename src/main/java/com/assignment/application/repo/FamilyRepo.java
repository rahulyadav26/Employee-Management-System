package com.assignment.application.repo;

import com.assignment.application.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FamilyRepo extends JpaRepository<Family,Long> {

    @Query("Select fam from Family fam where fam.emp_id=?1")
    List<Family> getListById(String empId);

    @Query("Select fam from Family fam where fam.emp_id = ?1 and fam.name = ?2")
    Family getFamInfo(String empId,String name);

}
