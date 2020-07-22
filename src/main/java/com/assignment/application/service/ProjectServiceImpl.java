package com.assignment.application.service;

import com.assignment.application.Constants.StringConstants;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Project;
import com.assignment.application.repo.CompanyRepo;
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

    @Autowired
    private StringConstants stringConstants;

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public Project addCompProject(Long companyId, Project project) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (project==null || company==null || !project.getCompanyId().equals(companyId) || project.getId().equals(0)) {
            return null;
        }
        return projectRepo.save(project);
    }

    @Override
    public String deleteProject(Long projectId, Long compId) {
        Company company = companyRepo.findById(compId).orElse(null);
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project == null || company==null || !project.getCompanyId().equals(compId)) {
            return stringConstants.invalidStatus;
        }
        projectRepo.deleteById(projectId);
        return stringConstants.deleteStatus;
    }

    @Override
    public List<Project> getProject(Long compId) {
        List<Project> projectList = projectRepo.getProjectListById(compId);
        Company company = companyRepo.findById(compId).orElse(null);
        if (projectList == null || company==null) {
            return null;
        }
        return projectList;
    }
}
