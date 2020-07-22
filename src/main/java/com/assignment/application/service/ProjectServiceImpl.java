package com.assignment.application.service;

import com.assignment.application.entity.Project;
import com.assignment.application.repo.ProjectRepo;
import com.assignment.application.service.interfaces.ProjectServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectServiceI {

    @Autowired
    private ProjectRepo projectRepo;


    @Override
    public Project addCompProject(Long companyId, Project project) {
        if (project==null || !project.getCompanyId().equals(companyId) || project.getId().equals(0)) {
            return null;
        }
        return projectRepo.save(project);
    }

    @Override
    public String deleteProject(Long projectId, Long compId) {
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project == null || !project.getCompanyId().equals(compId)) {
            return "Invalid credentials";
        }
        projectRepo.deleteById(projectId);
        return "Deletion Successful";
    }

    @Override
    public List<Project> getProject(Long compId) {
        List<Project> projectList = projectRepo.getProjectListById(compId);
        if (projectList == null) {
            return null;
        }
        return projectList;
    }
}
