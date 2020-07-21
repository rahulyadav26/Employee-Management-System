package com.assignment.application.controller;

import com.assignment.application.entity.Project;
import com.assignment.application.other.VerifyUser;
import com.assignment.application.service.interfaces.ProjectServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "{comp_id}/project")
public class ProjectController {

    @Autowired
    private ProjectServiceI projectServiceI;

    @Autowired
    private VerifyUser verifyUser;

    @DeleteMapping(value="/{project_id}" )
    public ResponseEntity<String> deleteProject(@PathVariable("project_id") Long projectId,
                                                @PathVariable("comp_id") Long companyId,
                                                @RequestHeader("username") String username,
                                                @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return projectServiceI.deleteProject(projectId, companyId);
        }
    }

    @PostMapping(value="")
    public ResponseEntity<Project> addProject(@PathVariable("comp_id") Long companyId,
                                              @RequestBody Project project,
                                              @RequestHeader("username") String username,
                                              @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return projectServiceI.addCompProject(companyId, project);
        }
    }

    @GetMapping(value="")
    public ResponseEntity<List<Project>> getProject(@PathVariable("comp_id") Long companyId,
                                                    @RequestHeader("username") String username,
                                                    @RequestHeader("password") String password){
        int status = verifyUser.authorizeUser(username,password);
        if(status==0){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return projectServiceI.getProject(companyId);
        }
    }

}
