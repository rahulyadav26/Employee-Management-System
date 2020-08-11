package com.assignment.application.authenticator;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.enums.RoleName;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.exception.UnauthorisedException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class VerifyUsers {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private RedisService redisService;

    public String authorizeUser(String token, String url, String type) {
        String cachedInfo = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX + token);
        if (cachedInfo.indexOf("superadmin") == -1) {
            String[] str = cachedInfo.split("employeeId='");
            String[] findId = str[str.length - 1].split("'");
            String employeeId = findId[0];
            Employee employee = employeeRepo.getEmployee(employeeId);
            if (employee.getRoleName().equals(RoleName.EMPLOYEE.toString())) {
                return employeeRole(employee, url, type);
            }
            return adminRole(employee, url, type);
        }
        return "0";
    }

    public String employeeRole(Employee employee, String url, String type) {
        String[] urlSplit = url.split("/");
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (employee.getDepartmentId() != 0 && (department == null || department.getIsActive() == 0)) {
            throw new NotExistsException("Not a valid user");
        }
        Company company = null;
        if (employee.getDepartmentId()!=0) {
            company = companyRepo.findById(department.getCompanyId()).orElse(null);
        }
        if (employee.getDepartmentId() != 0 && (company == null || company.getIsActive() == 0)) {
            throw new NotExistsException("Not a valid user");
        }
        if (urlSplit.length == 3 &&
            (urlSplit[2].equalsIgnoreCase("salary") || urlSplit[2].equalsIgnoreCase("update-employee-info")) &&
            (type.equalsIgnoreCase("GET") || type.equalsIgnoreCase("PATCH"))) {
            if (urlSplit[1].equalsIgnoreCase(employee.getEmployeeId()) &&
                ((urlSplit[0].equals("0") && employee.getDepartmentId()==0) ||
                 (company!=null && urlSplit[0].equals(Long.toString(company.getId()))))) {
                return employee.getEmployeeId();
            }
            throw new UnauthorisedException("Not allowed to access");
        }
        throw new UnauthorisedException("Not allowed to access");
    }

    public String adminRole(Employee employee, String url, String type) {
        if (url.indexOf("department-list") == -1) {
            if (url.indexOf("company") == 1 &&
                url.indexOf("complete-info") == -1 && url.indexOf("company-update") == -1) {
                throw new UnauthorisedException("Not allowed to access");
            }
            if (url.equals("/company") || url.equals("/department") || url.equals("/salary")) {
                throw new UnauthorisedException("Not allowed to access");
            }
            boolean isValid = Pattern.matches("company/\\d+",url);
            if(isValid){
                throw new UnauthorisedException("Not allowed to access");
            }
            Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
            if (department == null || department.getIsActive() == 0) {
                throw new NotExistsException("Not a valid user");
            }
            Company company = companyRepo.findById(department.getCompanyId()).orElse(null);
            if (company == null || company.getIsActive() == 0) {
                throw new NotExistsException("Not a valid user");
            }
            String[] urlSplit = url.split("/");
            if (urlSplit[0].equals(Long.toString(company.getId())) ||
                urlSplit[1].equals(Long.toString(company.getId()))) {
                return employee.getEmployeeId();
            }
            throw new UnauthorisedException("Not allowed to access");
        }
        throw new UnauthorisedException("Not allowed to access");
    }
}
