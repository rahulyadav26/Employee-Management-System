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
    public ResponseEntity<Project> addCompProject(Long companyId, Project project) {
        try{
            if(project.getCompanyId()!=companyId || project.getId()==0){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            return new ResponseEntity<>(projectRepo.save(project),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteProject(Long projectId,Long compId) {
        try{

            Project project = projectRepo.findById(projectId).orElse(null);
            if(project.getCompanyId()!=compId){
                return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
            }
            projectRepo.deleteById(projectId);
            return new ResponseEntity<>("Deletion Successful",HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error while deleting", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Project>> getProject(Long compId) {
        try{
            List<Project> projectList = projectRepo.getProjectListById(compId);
            return new ResponseEntity<>(projectList,HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
