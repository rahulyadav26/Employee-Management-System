package com.assignment.application.interceptor;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.entity.Department;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.AuthenticationException;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.DepartmentRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CompanyRepo companyRepo;

    private final Logger LOG = LogManager.getLogger(AuthInterceptor.class);

    private Long companyId = 0L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equalsIgnoreCase("POST") &&
            validateUrl(request.getRequestURL().toString())) {
            String userName = request.getHeader("username");
            String password = request.getHeader("password");
            if (userName == null || password == null || userName.isEmpty() || password.isEmpty()) {
                throw new AuthenticationException("Username and/or password not found");
            }
            String decodedPassword = "superadmin";
            if (userName.equalsIgnoreCase("superadmin")) {
                if (decodedPassword.equalsIgnoreCase(new String(Base64.getDecoder().decode(password)))) {
                    String checkIfExist = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_GENERATED + userName);
                    if (checkIfExist == null) {
                        return true;
                    }
                    throw new AuthenticationException("User already verified");
                }
                throw new AuthenticationException("Password is incorrect");
            }
            return authenticateCredential(userName,password);
        }
        String accessToken = request.getHeader("access_token");
        return tokenAlreadyGenerated(accessToken);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    public boolean validateUrl(String url) {
        String[] splitUrl = url.split("//");
        if (splitUrl.length != 3) {
            return false;
        }
        String[] urlInfo = splitUrl[2].split("/");
        if (urlInfo.length != 3 || !urlInfo[0].equalsIgnoreCase("company")
            || !Character.isDigit(urlInfo[1].charAt(0)) || !urlInfo[2].equalsIgnoreCase("signUp")) {
            return false;
        }
        Company company = companyRepo.findById(Long.parseLong(urlInfo[1])).orElse(null);
        if (company != null && company.getIsActive()==1) {
            companyId = Long.parseLong(urlInfo[1]);
            return true;
        }
        return false;
    }

    public boolean tokenAlreadyGenerated(String accessToken) {
        if (accessToken == null) {
            throw new AuthenticationException("No access token found");
        }
        String cachedToken = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX + accessToken);
        if (cachedToken == null) {
            throw new AuthenticationException("Not a verified user");
        }
        return true;
    }

    public boolean authenticateCredential(String userName,String password){
        Employee employee = employeeRepo.getEmployee(userName);
        if (employee == null || employee.getIsActive()==0) {
            throw new NotExistsException("No such employee exists");
        }
        Department department = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        if (department == null || department.getIsActive()==0) {
            throw new NotExistsException("Department doesn't exists");
        }
        String checkIfExist =
                redisService.getKeyValue(StringConstant.ACCESS_TOKEN_GENERATED + employee.getEmployeeId());
        if (checkIfExist == null && employee.getEmployeeId().equalsIgnoreCase(userName) &&
            employee.getDob().equalsIgnoreCase(password) && companyId != 0 &&
            department.getCompanyId().equals(companyId)) {
            return true;
        }
        if(checkIfExist!=null){
            throw new AuthenticationException("Access token already generated");
        }
        throw new AuthenticationException("Invalid url or login credentials");
    }

}
