package com.assignment.application.service;

import com.assignment.application.entity.Project;
import com.assignment.application.repo.ProjectRepo;
import com.assignment.application.service.interfaces.ProjectServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectServiceInterface {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private Project project;

    @Override
    public ResponseEntity<Project> addCompProject(long compId, Project project) {
        try{
            if(project.getComp_id()!=compId){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
            this.project = projectRepo.save(project);
            return new ResponseEntity<>(this.project,HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteProject(long projectId,long compId) {
        try{

            project = projectRepo.findById(projectId).orElse(null);
            System.out.println(project.getComp_id() + " " + compId);
            if(project.getComp_id()!=compId){
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
    public ResponseEntity<List<Project>> getProject(long compId) {
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
