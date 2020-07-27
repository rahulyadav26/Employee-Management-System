package com.assignment.application.test.unit;

import com.assignment.application.Constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.exception.DuplicateDataException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.service.DepartmentServiceImpl;
import com.assignment.application.update.DepartmentInfoUpdate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepo departmentRepo;

    @Mock
    private CompanyRepo companyRepo;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_AddDepartment_fails() {
        //company doesn't exists in database
        final Long companyId = new Long(11L);
        Company company = new Company();
        when(companyRepo.findById(companyId)).thenReturn(null);
        Department department = new Department(1L, "Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        //action
        departmentService.addDepartment(companyId, department);
    }

    @Test(expected = DuplicateDataException.class)
    public void test_DepartmentExist_AddDepartment_fails() {
        //company department exists in database
        final Long companyId = new Long(11L);
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        Department department = new Department(1L, "Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        when((departmentRepo.getDeptByCompId(companyId, department.getName().toUpperCase()))).thenReturn(department);
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        departmentService.addDepartment(companyId, department);
    }

    @Test(expected = RuntimeException.class)
    public void test_DepartmentNameEmpty_AddDepartment_fails() {
        //department name is empty
        final Long companyId = new Long(11L);
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        Department department = new Department(1L, "", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        departmentService.addDepartment(companyId, department);
    }

    @Test(expected = RuntimeException.class)
    public void test_DepartmentNotBelongToCompany_AddDepartment_fails() {
        //companyId!=department.getCompanyId()
        final Long companyId = new Long(11L);
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        Department department = new Department(1L, "Engineering", 10L, 1000L, 10L, 20L, "Sundar Pichai");
        //action
        departmentService.addDepartment(companyId, department);
    }


    @Test
    public void test_AddDepartment_Success() {
        //company exist and department doesn't exists also its name is not empty
        final Long companyId = new Long(11L);
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(departmentRepo.save(department)).thenReturn(department);
        //action
        Department actualDepartment = departmentService.addDepartment(companyId, department);
        //result
        Assert.assertEquals(department, actualDepartment);
        verify(companyRepo).findById(companyId);
        verify(departmentRepo).getDeptByCompId(companyId, department.getName().toUpperCase());
        verify(departmentRepo).save(department);
    }

    @Test
    public void test_GetDepartments_Success() {
        //fetch the departments list of a company
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department("Engineering", 2L, 1000L, 10L, 20L, "Sundar Pichai"));
        departmentList.add(new Department("Marketing", 1L, 10030L, 10L, 20L, "Satya Nadela"));
        when(departmentRepo.findAll()).thenReturn(departmentList);
        //action
        List<Department> actualDepartmentList = departmentService.getDepartments();
        //result
        Assert.assertEquals(departmentList, actualDepartmentList);
        verify(departmentRepo).findAll();
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_UpdateDepartment_fails() {
        //company doesn't exists in database
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        DepartmentInfoUpdate departmentInfoUpdate = new DepartmentInfoUpdate("Sundar Pichai", "", "100", "");
        when(companyRepo.findById(companyId)).thenReturn(null);
        //action
        departmentService.updateDepartmentInfo(companyId, departmentId, departmentInfoUpdate);
    }

    @Test(expected = RuntimeException.class)
    public void test_DepartmentNotExist_UpdateDepartment_fails() {
        //department doesn't exists in database
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        DepartmentInfoUpdate departmentInfoUpdate = new DepartmentInfoUpdate("Sundar Pichai", "", "100", "");
        when(departmentRepo.findById(departmentId)).thenReturn(null);
        //action
        departmentService.updateDepartmentInfo(companyId, departmentId, departmentInfoUpdate);
    }

    @Test(expected = RuntimeException.class)
    public void test_DepartmentCompanyIdNotMatches_UpdateDepartment_fails() {
        //companyId!=department.getCompanyId()
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 2L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        DepartmentInfoUpdate departmentInfoUpdate = new DepartmentInfoUpdate("Sundar Pichai", "", "100", "");
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        departmentService.updateDepartmentInfo(companyId, departmentId, departmentInfoUpdate);
    }

    @Test(expected = EmptyUpdateException.class)
    public void test_DepartmentInfoUpdateIsEmpty_UpdateDepartment_fails() {
        //department info update is empty
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        DepartmentInfoUpdate departmentInfoUpdate = null;
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        departmentService.updateDepartmentInfo(companyId, departmentId, departmentInfoUpdate);
    }

    @Test
    public void test_UpdateDepartment_Success() {
        //assumption
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        DepartmentInfoUpdate departmentInfoUpdate = new DepartmentInfoUpdate("Sundar Pichai", "", "100", "");
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        String actualResult = departmentService.updateDepartmentInfo(companyId, departmentId, departmentInfoUpdate);
        //result
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL, actualResult);
        verify(departmentRepo).findById(departmentId);
        verify(companyRepo).findById(companyId);
        verify(departmentRepo).save(department);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_GetDepartmentOfCompany_fails(){
        //companyId doesn't exists in db
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(null);
        //action
        departmentService.getDepartment(companyId,departmentId);
    }

    @Test(expected = RuntimeException.class)
    public void test_DepartmentNotExist_GetDepartmentOfCompany_fails(){
        //department not exist in db
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(departmentRepo.findById(departmentId)).thenReturn(null);
        //action
        departmentService.getDepartment(companyId,departmentId);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyIdMismatch_GetDepartmentOfCompany_fails(){
        //companyId!=department.companyId()
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 10L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        departmentService.getDepartment(companyId,departmentId);
    }

    @Test
    public void test_GetDepartmentOfCompany_Success(){
        //assumption
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        Department actualDepartment = departmentService.getDepartment(companyId,departmentId);
        //result
        Assert.assertEquals(department,actualDepartment);
        verify(companyRepo).findById(companyId);
        verify(departmentRepo).findById(departmentId);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyNotExist_DeleteDepartment_fails(){
        //companyId doesn't exists in db
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        //action
        departmentService.deleteDepartmentOfCompany(companyId,departmentId);
    }

    @Test(expected = RuntimeException.class)
    public void test_DepartmentNotExist_DeleteDepartment_fails(){
        //department not exist in Database
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department();
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        //action
        departmentService.deleteDepartmentOfCompany(companyId,departmentId);
    }

    @Test(expected = RuntimeException.class)
    public void test_CompanyIdMismatch_DeleteDepartment_fails(){
        //companyId!=department.companyId()
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 10L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        //action
        departmentService.deleteDepartmentOfCompany(companyId,departmentId);
    }

    @Test
    public void test_DeleteDepartment_Success(){
        //assumption
        final Long companyId = new Long(11L);
        final Long departmentId = new Long(1L);
        Department department = new Department("Engineering", 11L, 1000L, 10L, 20L, "Sundar Pichai");
        Company company = new Company(companyId, "Google", "Technology", 1000000L, "California", "Bill Gates");
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        String actualResult = departmentService.deleteDepartmentOfCompany(departmentId,companyId);
        //result
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,actualResult);
        verify(companyRepo).findById(companyId);
        verify(departmentRepo).findById(departmentId);
        verify(departmentRepo).deleteById(departmentId);
    }

}
