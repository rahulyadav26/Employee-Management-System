package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Project;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.ProjectRepo;
import com.assignment.application.service.interfaces.ProjectServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectServiceI {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public Project addCompProject(Long companyId, Project project) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (project==null || company==null || !project.getCompanyId().equals(companyId)) {
            throw new RuntimeException("Data not valid");
        }
        Project tempProject = projectRepo.save(project);
        return tempProject;
    }

    @Override
    public String deleteProject(Long projectId, Long compId) {
        Company company = companyRepo.findById(compId).orElse(null);
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project == null || company==null || !project.getCompanyId().equals(compId)) {
            throw new RuntimeException("Data not valid");
        }
        projectRepo.deleteById(projectId);
        return StringConstant.DELETION_SUCCESSFUL;
    }

    @Override
    public List<Project> getProject(Long compId) {
        Company company = companyRepo.findById(compId).orElse(null);
        if (company==null) {
            throw new RuntimeException("Data not valid");
        }
        List<Project> projectList = projectRepo.getProjectListById(compId);
        return projectList;
    }
}
