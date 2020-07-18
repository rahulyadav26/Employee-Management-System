package com.assignment.application.service.interfaces;

import com.assignment.application.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProjectServiceInterface {

    ResponseEntity<Project> addCompProject(long compId,Project project);

    ResponseEntity<String> deleteProject(long projectId,long compId);

    ResponseEntity<List<Project>> getProject(long compId);

}
