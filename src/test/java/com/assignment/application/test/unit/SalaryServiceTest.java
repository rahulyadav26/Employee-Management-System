//package com.assignment.application.test.unit;
//
//import com.assignment.application.constants.StringConstant;
//import com.assignment.application.entity.Company;
//import com.assignment.application.entity.Department;
//import com.assignment.application.entity.Employee;
//import com.assignment.application.entity.Salary;
//import com.assignment.application.exception.DataMismatchException;
//import com.assignment.application.exception.EmptyUpdateException;
//import com.assignment.application.exception.InsufficientInformationException;
//import com.assignment.application.exception.NotExistsException;
//import com.assignment.application.repo.CompanyRepo;
//import com.assignment.application.repo.DepartmentRepo;
//import com.assignment.application.repo.EmployeeRepo;
//import com.assignment.application.repo.SalaryRepo;
//import com.assignment.application.service.CachingInfo;
//import com.assignment.application.service.SalaryServiceImpl;
//import com.assignment.application.update.SalaryEmployeeUpdate;
//import com.assignment.application.update.SalaryUpdate;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SalaryServiceTest {
//
//    @InjectMocks
//    private SalaryServiceImpl salaryService;
//
//    @Mock
//    private SalaryRepo salaryRepo;
//
//    @Mock
//    private DepartmentRepo departmentRepo;
//
//    @Mock
//    private CompanyRepo companyRepo;
//
//    @Mock
//    private CachingInfo cachingInfo;
//
//    @Mock
//    private EmployeeRepo employeeRepo;
//
//    @Mock
//    private KafkaTemplate<String, SalaryUpdate> kafkaTemplateSalary;
//
//    @Test(expected = NotExistsException.class)
//    public void test_CompanyNotExist_AddSalary_fails(){
//        //company not exist
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        //action
//        salaryService.addSalary(companyId,employeeId,salary);
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_EmployeeNotExists_AddSalary_fails(){
//        //employee not exists
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company("Google", "Technology", "California", "Bill Gates");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        //action
//        salaryService.addSalary(companyId,employeeId,salary);
//    }
//
//    @Test(expected = DataMismatchException.class)
//    public void test_CompanyIdMismatch_AddSalary_fails(){
//        //companyId!=salary.companyId
//        final Long companyId = new Long(10L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(employeeId)).thenReturn(employee);
//        //action
//        salaryService.addSalary(companyId,employeeId,salary);
//    }
//
//    @Test(expected = DataMismatchException.class)
//    public void test_EmployeeIdMismatch_AddSalary_fails(){
//        //employeeId!=salary.employeeId
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_2");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company(companyId,"Google", "Technology", "California", "Bill Gates");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(employeeId)).thenReturn(employee);
//        //action
//        salaryService.addSalary(companyId,employeeId,salary);
//    }
//
//    @Test(expected = InsufficientInformationException.class)
//    public void test_SalaryInfoNull_AddSalary_fails(){
//        //salary info null or salary.getSalary() is empty
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_2");
//        Salary salary = null;
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(employeeId)).thenReturn(employee);
//        //action
//        salaryService.addSalary(companyId,employeeId,salary);
//    }
//
//    @Test
//    public void test_AddSalary_Success(){
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(employeeId)).thenReturn(employee);
//        when(salaryRepo.save(salary)).thenReturn(salary);
//        //action
//        Salary actualSalary = salaryService.addSalary(companyId,employeeId,salary);
//        //result
//        Assert.assertEquals(salary,actualSalary);
//        verify(companyRepo).findById(companyId);
//        verify(employeeRepo).getEmployee(employeeId);
//        verify(salaryRepo).save(salary);
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_CompanyNotExist_GetSalaryOfEmp_fails(){
//        //company not exist
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"goole_3,");
//        //action
//        salaryService.getSalary(companyId,employeeId);
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_EmployeeInfoNotExists_GetSalaryOfEmp_fails(){
//        //employee info not exists
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company(companyId,"Google", "Technology", "California", "Bill Gates");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        //action
//        salaryService.getSalary(companyId,employeeId);
//    }
//
//    @Test
//    public void test_GetSalaryOfEmp_Success(){
//        //company exists
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3,");
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(salaryRepo.getSalaryById(employeeId)).thenReturn(salary);
//        //action
//        Salary actualSalary = salaryService.getSalary(companyId,employeeId);
//        //result
//        Assert.assertEquals(salary,actualSalary);
//        verify(companyRepo).findById(companyId);
//        verify(salaryRepo).getSalaryById(employeeId);
//
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_CompanyNotExist_UpdateSalary_fails(){
//        //company not exist
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        SalaryUpdate salaryUpdate = new SalaryUpdate("0","1","Engineering",11L,100);
//        //action
//        salaryService.updateSalary(companyId,salaryUpdate);
//    }
//
//    @Test(expected = EmptyUpdateException.class)
//    public void test_SalaryUpdateInfoNull_UpdateSalary_fails(){
//        //salary update info is null
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryUpdate salaryUpdate = null;
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        //action
//        salaryService.updateSalary(companyId,salaryUpdate);
//    }
//
//    @Test
//    public void test_CompanySalaryUpdateByAmount_UpdateSalary_success(){
//        //update salary of the entire company by some amount
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        List<Salary> salaryList = new ArrayList<>();
//        salaryList.add(new Salary("google_3",100000d,"12343234323444",11L,1L));
//        salaryList.add(new Salary("google_2",1540000d,"44555455333444",11L,1L));
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryUpdate salaryUpdate = new SalaryUpdate("0","0","",11L,1000);
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(salaryRepo.salaryListComp(companyId)).thenReturn(salaryList);
//        //action
//        String actualResult = salaryService.updateSalary(companyId,salaryUpdate);
//        //result
//        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
//        verify(companyRepo).findById(companyId);
//        verify(salaryRepo).salaryListComp(companyId);
//        verify(cachingInfo).updateSalary(salaryList,companyId);
//    }
//
//    @Test
//    public void test_CompanySalaryUpdateByPercentage_UpdateSalary_success(){
//        //update salary of the entire company by percentage
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        List<Salary> salaryList = new ArrayList<>();
//        salaryList.add(new Salary("google_3",100000d,"12343234323444",11L,1L));
//        salaryList.add(new Salary("google_2",1540000d,"44555455333444",11L,1L));
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryUpdate salaryUpdate = new SalaryUpdate("0","1","",11L,5);
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(salaryRepo.salaryListComp(companyId)).thenReturn(salaryList);
//        //action
//        String actualResult = salaryService.updateSalary(companyId,salaryUpdate);
//        //result
//        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
//        verify(companyRepo).findById(companyId);
//        verify(salaryRepo).salaryListComp(companyId);
//        verify(cachingInfo).updateSalary(salaryList,companyId);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void test_CompanyDepartmentNotExists_UpdateSalary_fails(){
//        //update salary of the department of a company by percentage but department not exists
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryUpdate salaryUpdate = new SalaryUpdate("1","0","Engineering",11L,5);
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        //action
//        String actualResult = salaryService.updateSalary(companyId,salaryUpdate);
//    }
//
//    @Test
//    public void test_CompanyDepartmentSalaryUpdateByPercentage_UpdateSalary_success(){
//        //update salary of the company department by percentage
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        List<Salary> salaryList = new ArrayList<>();
//        salaryList.add(new Salary("google_3",100000d,"12343234323444",11L,1L));
//        salaryList.add(new Salary("google_2",1540000d,"44555455333444",11L,1L));
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        Department department = new Department("Engineering", 11L, "Sundar Pichai");
//        SalaryUpdate salaryUpdate = new SalaryUpdate("1","0","engineering",11L,5);
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(departmentRepo.getDeptByCompId(companyId,department.getName().toUpperCase())).thenReturn(department);
//        when(salaryRepo.salaryListCompDept(companyId,department.getId())).thenReturn(salaryList);
//        //action
//        String actualResult = salaryService.updateSalary(companyId,salaryUpdate);
//        //result
//        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
//        verify(companyRepo).findById(companyId);
//        verify(salaryRepo).salaryListCompDept(companyId,department.getId());
//        verify(departmentRepo).getDeptByCompId(companyId,department.getName().toUpperCase());
//        verify(cachingInfo).updateSalary(salaryList,companyId);
//    }
//
//    @Test
//    public void test_CompanyDepartmentSalaryUpdateByAmount_UpdateSalary_success(){
//        //update salary of the company department by amount
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        List<Salary> salaryList = new ArrayList<>();
//        salaryList.add(new Salary("google_3",100000d,"12343234323444",11L,1L));
//        salaryList.add(new Salary("google_2",1540000d,"44555455333444",11L,1L));
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        Department department = new Department("Engineering", 11L,"Sundar Pichai");
//        SalaryUpdate salaryUpdate = new SalaryUpdate("1","1","engineering",11L,10000);
//        when(companyRepo.findById(companyId)).thenReturn(Optional.of(company));
//        when(departmentRepo.getDeptByCompId(companyId,department.getName().toUpperCase())).thenReturn(department);
//        when(salaryRepo.salaryListCompDept(companyId,department.getId())).thenReturn(salaryList);
//        //action
//        String actualResult = salaryService.updateSalary(companyId,salaryUpdate);
//        //result
//        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
//        verify(companyRepo).findById(companyId);
//        verify(salaryRepo).salaryListCompDept(companyId,department.getId());
//        verify(departmentRepo).getDeptByCompId(companyId,department.getName().toUpperCase());
//        verify(cachingInfo).updateSalary(salaryList,companyId);
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_CompanyNotExists_UpdateSalaryOfEmployee_fails(){
//        //company not exist
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        SalaryEmployeeUpdate salary = new SalaryEmployeeUpdate(1223d);
//        //action
//        salaryService.updateSalaryOfEmployee(companyId,employeeId,salary);
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_DepartmentNotExists_UpdateSalaryOfEmployee_fails(){
//        //department not exist
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryEmployeeUpdate salary = new SalaryEmployeeUpdate(1223d);
//        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
//        //action
//        salaryService.updateSalaryOfEmployee(companyId,employeeId,salary);
//    }
//
//    @Test(expected = InsufficientInformationException.class)
//    public void test_SalaryEmployeeUpdate_UpdateSalaryOfEmployee_fails(){
//        //salary employee update is null
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryEmployeeUpdate salary = null;
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3");
//        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
//        //action
//        salaryService.updateSalaryOfEmployee(companyId,employeeId,salary);
//    }
//
//    @Test(expected = DataMismatchException.class)
//    public void test_CompanyIdMismatch_UpdateSalaryOfEmployee_fails(){
//        //company id mismatch
//        final Long companyId = new Long(10L);
//        final String employeeId = new String("google_3");
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryEmployeeUpdate salary = new SalaryEmployeeUpdate(1223d);
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3");
//        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
//        //action
//        salaryService.updateSalaryOfEmployee(companyId,employeeId,salary);
//    }
//
//    @Test(expected = NotExistsException.class)
//    public void test_employeeSalaryNotPresent_UpdateSalaryOfEmployee_fails(){
//        //employee salary not present in database
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryEmployeeUpdate salary = new SalaryEmployeeUpdate(1223d);
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3");
//        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
//        //action
//        salaryService.updateSalaryOfEmployee(companyId,employeeId,salary);
//    }
//
//    @Test
//    public void test_UpdateSalaryOfEmployee_Success(){
//        //assumption
//        final Long companyId = new Long(11L);
//        final String employeeId = new String("google_3");
//        Company company = new Company(companyId,"Google", "Technology","California", "Bill Gates");
//        SalaryEmployeeUpdate salaryEmployeeUpdate = new SalaryEmployeeUpdate(1223d);
//        Employee employee = new Employee("Sundar Pichai","1/1/1995","California","California","12334567770","CEO",1L,1L,11L,"google_3");
//        Salary salary = new Salary("google_3",100000d,"12343234323444",11L,1L);
//        when(companyRepo.findById(anyLong())).thenReturn(Optional.of(company));
//        when(employeeRepo.getEmployee(anyString())).thenReturn(employee);
//        when(salaryRepo.getSalaryById(anyString())).thenReturn(salary);
//        //action
//        String actualResult = salaryService.updateSalaryOfEmployee(companyId,employeeId,salaryEmployeeUpdate);
//        //result
//        Assert.assertEquals(StringConstant.UPDATE_SUCCESSFUL,actualResult);
//        verify(companyRepo).findById(anyLong());
//        verify(employeeRepo).getEmployee(anyString());
//        verify(salaryRepo).getSalaryById(anyString());
//    }
//
//}
