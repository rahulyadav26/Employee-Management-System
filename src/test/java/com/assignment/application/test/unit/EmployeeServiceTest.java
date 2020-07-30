package com.assignment.application.test.unit;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.DataMismatchException;
import com.assignment.application.exception.EmptyUpdateException;
import com.assignment.application.exception.InsufficientInformationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.CachingInfo;
import com.assignment.application.service.EmployeeServiceImpl;
import com.assignment.application.service.RedisService;
import com.assignment.application.update.EmployeeInfoUpdate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private CachingInfo cachingInfo;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private KafkaTemplate<String, EmployeeInfoUpdate> kafkaTemplateEmployeeUpdate;

    @Mock
    private KafkaTemplate<String, Employee> kafkaTemplateEmployee;

    @Mock
    private RedisService redisService;

    @Test(expected = NotExistsException.class)
    public void test_CompanyNotExist_AddEmployee_fails(){
        //company not exist
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        //action
        employeeService.addEmployee(companyId,employee);
    }

    @Test(expected = InsufficientInformationException.class)
    public void test_EmployeeInfoNull_AddEmployee_fails(){
        //employee info null
        final Long companyId = new Long(11L);
        Employee employee = null;
        Company company = new Company("Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        employeeService.addEmployee(companyId,employee);
    }

    @Test(expected = DataMismatchException.class)
    public void test_CompanyIdMismatch_AddEmployee_fails(){
        //companyId!=employee.companyId
        final Long companyId = new Long(10L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        Company company = new Company(companyId,"Google", "Technology", "California", "Bill Gates");
        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
        //action
        employeeService.addEmployee(companyId,employee);
    }

    @Test
    public void test_AddEmployee_Success(){
        //assumption
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        Company company = new Company(companyId,"Google", "Technology", "California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(cachingInfo.addEmployee(employee)).thenReturn(employee);
        //action
        Employee actualEmployee = employeeService.addEmployee(companyId,employee);
        //result
        Assert.assertEquals(employee,actualEmployee);
        verify(companyRepo).findById(companyId);
        verify(cachingInfo).addEmployee(employee);
    }

    @Test(expected = NotExistsException.class)
    public void test_CompanyNotExist_GetEmployeeOfComp_fails(){
        //company not exist
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        //action
        employeeService.getEmployeesOfComp(companyId);
    }

    @Test
    public void test_GetEmployeeOfComp_Success(){
        //company exists
        final Long companyId = new Long(11L);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,"));
        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(cachingInfo.getEmployeeOfComp(companyId)).thenReturn(employeeList);
        //action
        List<Employee> actualResult = employeeService.getEmployeesOfComp(companyId);
        //result
        Assert.assertEquals(employeeList.size(),actualResult.size());
        Assert.assertEquals(employeeList.get(0),actualResult.get(0));
        verify(companyRepo).findById(companyId);
        verify(cachingInfo).getEmployeeOfComp(companyId);
    }

    @Test(expected = NotExistsException.class)
    public void test_CompanyNotExist_UpdateEmployeeInfo_fails(){
        //company not exist
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        EmployeeInfoUpdate employeeInfoUpdate = new EmployeeInfoUpdate("Califonia","","1234567890");
        //action
        employeeService.updateEmployeeInfo(employee.getEmployeeId(),companyId,employeeInfoUpdate);
    }

    @Test(expected = EmptyUpdateException.class)
    public void test_EmployeeInfoNull_UpdateEmployeeInfo_fails(){
        //employeeInfoUpdate is null
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
        EmployeeInfoUpdate employeeInfoUpdate = null;
        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        employeeService.updateEmployeeInfo(employee.getEmployeeId(),companyId,employeeInfoUpdate);
    }

    @Test
    public void test_UpdateEmployee_Success(){
        //assumption
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
        EmployeeInfoUpdate employeeInfoUpdate = new EmployeeInfoUpdate("Califonia","","1234567890");
        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(cachingInfo.updateEmployeeInfo(employee.getEmployeeId(),companyId,employeeInfoUpdate)).thenReturn(StringConstant.UPDATE_SUCCESSFUL);
        //action
        String actualResult = employeeService.updateEmployeeInfo(employee.getEmployeeId(),companyId,employeeInfoUpdate);
        //result
        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
        verify(companyRepo).findById(companyId);
        verify(cachingInfo).updateEmployeeInfo(employee.getEmployeeId(),companyId,employeeInfoUpdate);
    }

    @Test(expected = NotExistsException.class)
    public void test_CompanyNotExist_DeleteEmployee_fails(){
        //company not exist
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        //action
        employeeService.deleteEmployee(companyId,employee.getEmployeeId());
    }

    @Test(expected = NotExistsException.class)
    public void test_EmployeeNotExist_DeleteEmployee_fails(){
        //employee not exist
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        //action
        employeeService.deleteEmployee(companyId,employee.getEmployeeId());
    }

    @Test(expected = DataMismatchException.class)
    public void test_CompanyIdMismatch_DeleteEmployee_fails(){
        //companyId!=employee.companyId
        final Long companyId = new Long(10L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
        //action
        employeeService.deleteEmployee(companyId,employee.getEmployeeId());
    }

    @Test
    public void test_DeleteEmployee_Success(){
        //assumption
        final Long companyId = new Long(11L);
        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
        when(employeeRepo.getEmployee(employee.getEmployeeId())).thenReturn(employee);
        //action
        String actualResult = employeeService.deleteEmployee(companyId,employee.getEmployeeId());
        //result
        Assert.assertEquals(StringConstant.DELETION_SUCCESSFUL,actualResult);
        verify(companyRepo).findById(companyId);
        verify(employeeRepo).delete(employee);
    }

}
