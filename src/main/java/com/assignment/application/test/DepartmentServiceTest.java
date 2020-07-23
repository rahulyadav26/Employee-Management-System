package com.assignment.application.test;

import com.assignment.application.Constants.StringConstants;
import com.assignment.application.entity.Department;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.DepartmentServiceImpl;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private StringConstants stringConstants;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddDepartment(){
        //assumption company exists and department json is not null and companyId matched department companyId
        Department department = new Department("Engineering",2L,1000L,10L,20L,"Sundar Pichai");
        //action
        when(departmentService.addDepartment(anyLong(),any(Department.class))).thenReturn(department);
        //result
        Assert.assertEquals(Optional.ofNullable(department.getCompanyId()), Optional.of(2L));
        Assert.assertEquals(department.getName(),"Engineering");
        Assert.assertEquals(Optional.ofNullable(department.getEmployeeCount()), Optional.of(1000L));
        Assert.assertEquals(Optional.ofNullable(department.getOngoingProject()), Optional.of(10L));
        Assert.assertEquals(Optional.ofNullable(department.getCompletedProject()), Optional.of(20L));
        Assert.assertEquals(department.getHead(),"Sundar Pichai");

        //assumption company doesn't exists or companyId doesn't matches with department companyId
        Department departmentEmpty = null;
        //action
        when(departmentService.addDepartment(anyLong(),any(Department.class))).thenReturn(departmentEmpty);
        //result
        Assert.assertEquals(departmentEmpty,null);

        //assumption department json is null
        departmentEmpty = null;
        //action
        when(departmentService.addDepartment(anyLong(),any(Department.class))).thenReturn(departmentEmpty);
        //result
        Assert.assertEquals(departmentEmpty,null);
    }

    @Test
    public void testGetDepartmentList(){
        //assumption
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department("Engineering",2L,1000L,10L,20L,"Sundar Pichai"));
        departmentList.add(new Department("Marketing",1L,10030L,10L,20L,"Satya Nadela"));
        //action
        when(departmentService.getDepartments()).thenReturn(departmentList);
        //result
        Assert.assertEquals(departmentList.size(),2);
        Assert.assertEquals(Optional.ofNullable(departmentList.get(0).getCompanyId()), Optional.of(2L));
        Assert.assertEquals(departmentList.get(0).getName(),"Engineering");
        Assert.assertEquals(Optional.ofNullable(departmentList.get(0).getEmployeeCount()), Optional.of(1000L));
        Assert.assertEquals(Optional.ofNullable(departmentList.get(0).getOngoingProject()), Optional.of(10L));
        Assert.assertEquals(Optional.ofNullable(departmentList.get(0).getCompletedProject()), Optional.of(20L));
        Assert.assertEquals(departmentList.get(0).getHead(),"Sundar Pichai");
    }

    @Test
    public void testGetDepartmentCompany(){
        //assumption company and department exists and department.companyId==companyId
        Department department= new Department("Engineering",2L,1000L,10L,20L,"Sundar Pichai");
        //action
        when(departmentService.getDepartment(anyLong(),anyLong())).thenReturn(department);
        //result
        Assert.assertEquals(Optional.ofNullable(department.getCompanyId()), Optional.of(2L));
        Assert.assertEquals(department.getName(),"Engineering");
        Assert.assertEquals(Optional.ofNullable(department.getEmployeeCount()), Optional.of(1000L));
        Assert.assertEquals(Optional.ofNullable(department.getOngoingProject()), Optional.of(10L));
        Assert.assertEquals(Optional.ofNullable(department.getCompletedProject()), Optional.of(20L));
        Assert.assertEquals(department.getHead(),"Sundar Pichai");

        //assumption companyId!=department.companyId
        Department departmentEmpty = null;
        //action
        when(departmentService.getDepartment(anyLong(),anyLong())).thenReturn(departmentEmpty);
        Assert.assertEquals(departmentEmpty,null);

        //assumption company or/and department doesn't exists
        departmentEmpty = null;
        //action
        when(departmentService.getDepartment(anyLong(),anyLong())).thenReturn(departmentEmpty);
        //result
        Assert.assertEquals(departmentEmpty,null);

        //assumption department json is null
        departmentEmpty = null;
        //action
        when(departmentService.getDepartment(anyLong(),anyLong())).thenReturn(departmentEmpty);
        //result
        Assert.assertEquals(departmentEmpty,null);
    }

    @Test
    public void testUpdateDepartmentInfo(){
        //assumption company and department exists and department.companyId==companyId and DepartmentInfoUpdate json is not null
        String result = stringConstants.updateStatus;
        //action
        when(departmentService.updateDepartmentInfo(anyLong(),anyLong(),any(DepartmentInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.updateStatus);

        //assumption companyId!=department.companyId and DepartmentInfoUpdate is not null
        result = stringConstants.invalidStatus;
        //action
        when(departmentService.updateDepartmentInfo(anyLong(),anyLong(),any(DepartmentInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);

        //assumption companyId==department.companyId and either or both of them doesn't exists and DepartmentInfoUpdate is not null
        result = stringConstants.invalidStatus;
        //action
        when(departmentService.updateDepartmentInfo(anyLong(),anyLong(),any(DepartmentInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);

        //assumption DepartmentInfoUpdate is null
        result = stringConstants.invalidStatus;
        //action
        when(departmentService.updateDepartmentInfo(anyLong(),anyLong(),any(DepartmentInfoUpdate.class))).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);
    }

    @Test
    public void testDeleteDepartmentOfCompany(){
        //assumption company and department exists and department.companyId==companyId
        String result = stringConstants.deleteStatus;
        //action
        when(departmentService.deleteDepartmentOfCompany(anyLong(),anyLong())).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.deleteStatus);

        //assumption companyId!=department.companyId
        result = stringConstants.invalidStatus;
        //action
        when(departmentService.deleteDepartmentOfCompany(anyLong(),anyLong())).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);

        //assumption companyId==department.companyId and either or both of them doesn't exists
        result = stringConstants.invalidStatus;
        //action
        when(departmentService.deleteDepartmentOfCompany(anyLong(),anyLong())).thenReturn(result);
        //result
        Assert.assertEquals(result,stringConstants.invalidStatus);
    }

}
