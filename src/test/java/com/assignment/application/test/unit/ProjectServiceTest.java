package com.assignment.application.test.unit;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.Project;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.ProjectRepo;
import com.assignment.application.service.ProjectServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private ProjectRepo projectRepo;

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_AddProject_fails() {
        //company not exist
        final Long companyId = new Long(11L);
        Project project = new Project(1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        //action
        projectService.addCompProject(companyId, project);
    }

    @Test(expected = RuntimeException.class)
    public void test_ProjectInfoNull_AddProject_fails() {
        //project info null
        final Long companyId = new Long(11L);
        Project project = null;
        Company company = new Company("Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        projectService.addCompProject(companyId, project);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyIdMismatch_AddProject_fails() {
        //companyId!=project.companyId
        final Long companyId = new Long(10L);
        Project project = new Project(1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        projectService.addCompProject(companyId, project);
    }

    @Test
    public void test_AddProject_Success() {
        //assumption
        final Long companyId = new Long(11L);
        Project project = new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(projectRepo.save(any(Project.class))).thenReturn(project);
        //action
        Project actualProject = projectService.addCompProject(companyId, project);
        //result
        Assert.assertEquals(project, actualProject);
        verify(companyRepo).findById(companyId);
        verify(projectRepo).save(project);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_GetProjectOfComp_fails() {
        //company not exist
        final Long companyId = new Long(11L);
        Project project = new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        //action
        projectService.getProject(companyId);
    }

    @Test
    public void test_GetProjectOfComp_Success() {
        //company exists
        final Long companyId = new Long(11L);
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3"));
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(projectRepo.getProjectListById(companyId)).thenReturn(projectList);
        //action
        List<Project> actualProject = projectService.getProject(companyId);
        //result
        Assert.assertEquals(projectList.size(), actualProject.size());
        Assert.assertEquals(projectList.get(0), actualProject.get(0));
        verify(companyRepo).findById(companyId);
        verify(projectRepo).getProjectListById(companyId);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_DeleteProject_fails() {
        //company not exist
        final Long companyId = new Long(11L);
        final Long projectId = new Long(1L);
        Project project = new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        //action
        projectService.deleteProject(projectId, companyId);
    }

    @Test(expected = RuntimeException.class)
    public void test_ProjectNotExist_DeleteProject_fails() {
        //project not exists
        final Long companyId = new Long(11L);
        Project project = new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        projectService.deleteProject(project.getId(), companyId);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyIdMismatch_DeleteProject_fails() {
        //companyId!=project.companyId
        final Long companyId = new Long(10L);
        Project project = new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(projectRepo.findById(project.getId())).thenReturn(Optional.of(project));
        //action
        projectService.deleteProject(project.getId(), companyId);
    }

    @Test
    public void test_DeleteProject_Success() {
        //assumption
        final Long companyId = new Long(11L);
        Project project = new Project(1L, 1L, 11L, "ABCD", "Sundar Pichai", "google_3");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(projectRepo.findById(project.getId())).thenReturn(Optional.of(project));
        //action
        String actualResult = projectService.deleteProject(project.getId(),companyId);
        //result
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,actualResult);
        verify(companyRepo).findById(companyId);
        verify(projectRepo).findById(project.getId());
        verify(projectRepo).deleteById(project.getId());
    }

}
