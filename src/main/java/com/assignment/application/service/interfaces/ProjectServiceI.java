package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProjectServiceI {

    ResponseEntity<Project> addCompProject(Long companyId,Project project);

    ResponseEntity<String> deleteProject(Long projectId,Long companyId);

    ResponseEntity<List<Project>> getProject(Long companyId);

}
