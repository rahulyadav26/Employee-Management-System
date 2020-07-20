package com.assignment.application.repo;

import com.assignment.application.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project,Long>{

    @Query("Select pro from Project pro where pro.companyId=?1")
    List<Project> getProjectListById(Long compId);
}
