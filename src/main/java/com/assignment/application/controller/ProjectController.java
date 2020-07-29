//package com.assignment.application.controller;
//
//import com.assignment.application.Constants.StringConstant;
//import com.assignment.application.authenticator.VerifyUser;
//import com.assignment.application.entity.Project;
//import com.assignment.application.service.interfaces.ProjectServiceI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "{comp_id}/project")
//public class ProjectController {
//
//    @Autowired
//    private ProjectServiceI projectServiceI;
//
//    @Autowired
//    private VerifyUser verifyUser;
//
//    @DeleteMapping(value = "/{project_id}")
//    public ResponseEntity<String> deleteProject(@PathVariable("project_id") Long projectId,
//                                                @PathVariable("comp_id") Long companyId,
//                                                @RequestHeader("username") String username,
//                                                @RequestHeader("password") String password) {
//        try {
//            int status = verifyUser.authorizeUser(username, password);
//            if (status == 0) {
//                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//            }
//            if (projectServiceI.deleteProject(projectId, companyId).equalsIgnoreCase(StringConstant.DELETION_SUCCESSFUL)) {
//                return new ResponseEntity<>(StringConstant.DELETION_SUCCESSFUL, HttpStatus.OK);
//            }
//            return new ResponseEntity<>(StringConstant.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping(value = "")
//    public ResponseEntity<Project> addProject(@PathVariable("comp_id") Long companyId,
//                                              @RequestBody Project project,
//                                              @RequestHeader("username") String username,
//                                              @RequestHeader("password") String password) {
//        try {
//            int status = verifyUser.authorizeUser(username, password);
//            if (status == 0) {
//                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//            }
//            Project projectToBeAdded = projectServiceI.addCompProject(companyId, project);
//            if (projectToBeAdded == null) {
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            }
//            return new ResponseEntity<>(projectToBeAdded, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @GetMapping(value = "")
//    public ResponseEntity<List<Project>> getProject(@PathVariable("comp_id") Long companyId,
//                                                    @RequestHeader("username") String username,
//                                                    @RequestHeader("password") String password) {
//        try {
//            int status = verifyUser.authorizeUser(username, password);
//            if (status == 0) {
//                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//            }
//            List<Project> projectList = projectServiceI.getProject(companyId);
//            if (projectList == null) {
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            }
//            return new ResponseEntity<>(projectList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//}
