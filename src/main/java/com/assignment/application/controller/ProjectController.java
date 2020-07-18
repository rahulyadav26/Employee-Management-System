package com.assignment.application.controller;

import com.assignment.application.entity.Project;
import com.assignment.application.service.interfaces.ProjectServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "{comp_id}/project")
public class ProjectController {

    @Autowired
    ProjectServiceInterface projectServiceInterface;

    @RequestMapping(value="/{project_id}" , method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProject(@PathVariable("project_id") long projectId,
                                                @PathVariable("comp_id") long compId){
        return projectServiceInterface.deleteProject(projectId,compId);
    }

    @RequestMapping(value="" , method = RequestMethod.POST)
    public ResponseEntity<Project> addProject(@PathVariable("comp_id") long comp_id,@RequestBody Project project){
        return projectServiceInterface.addCompProject(comp_id,project);
    }

    @RequestMapping(value="" , method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getProject(@PathVariable("comp_id") long comp_id){
        return projectServiceInterface.getProject(comp_id);
    }

}
