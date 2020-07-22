package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProjectServiceI {

    Project addCompProject(Long companyId,Project project);

    String deleteProject(Long projectId,Long companyId);

    List<Project> getProject(Long companyId);

}
