package com.assignment.application.controller;

import com.assignment.application.entity.Project;
import com.assignment.application.service.interfaces.ProjectServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "{comp_id}/project")
public class ProjectController {

    @Autowired
    ProjectServiceI projectServiceI;

    @DeleteMapping(value="/{project_id}" )
    public ResponseEntity<String> deleteProject(@PathVariable("project_id") Long projectId,
                                                @PathVariable("comp_id") Long companyId){
        return projectServiceI.deleteProject(projectId,companyId);
    }

    @PostMapping(value="")
    public ResponseEntity<Project> addProject(@PathVariable("comp_id") Long companyId,@RequestBody Project project){
        return projectServiceI.addCompProject(companyId,project);
    }

    @GetMapping(value="")
    public ResponseEntity<List<Project>> getProject(@PathVariable("comp_id") Long companyId){
        return projectServiceI.getProject(companyId);
    }

}
