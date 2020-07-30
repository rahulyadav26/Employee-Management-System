package com.assignment.application.interceptor;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Employee;
import com.assignment.application.exception.NotExistsException;
import com.assignment.application.exception.AuthenticationException;
import com.assignment.application.repo.CompanyRepo;
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
    private RedisService redisService;

    @Autowired
    private CompanyRepo companyRepo;

    private final String START_TIME = "Start time";

    private final Logger LOG = LogManager.getLogger(AuthInterceptor.class);

    private Long companyId = 0L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        if (request.getMethod().equalsIgnoreCase("POST") &&
                validateUrl(request.getRequestURL().toString())) {
            String userName = request.getHeader("username");
            String password = request.getHeader("password");
            if (userName == null || password == null) {
                throw new AuthenticationException("Username and password not found");
            }
            String decodedPassword = new String(Base64.getDecoder().decode(password));
            if (userName.equalsIgnoreCase("superadmin")) {
                if (decodedPassword.equalsIgnoreCase("superadmin")) {
                    String checkIfExist = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_GENERATED + userName);
                    if (checkIfExist == null) {
                        return true;
                    }
                    throw new AuthenticationException("User already verified");
                }
                throw new AuthenticationException("Password is incorrect");
            }
            Employee employee = employeeRepo.getEmployee(userName);
            if (employee == null) {
                throw new NotExistsException("No such employee exists");
            }
            String checkIfExist = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_GENERATED + employee.getEmployeeId());
            if (checkIfExist == null && employee.getEmployeeId().equalsIgnoreCase(userName) &&
                    employee.getDob().equalsIgnoreCase(decodedPassword) &&
                    companyId != 0 && employee.getCompanyId().equals(companyId)) {
                return true;
            }
            throw new AuthenticationException("Invalid url or login credentials");
        }
        String accessToken = request.getHeader("access_token");
        if (accessToken == null) {
            throw new AuthenticationException("No access token found");
        }
        String cachedToken = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX + accessToken);
        if (cachedToken == null) {
            throw new AuthenticationException("Not a verified user");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long endTime = System.currentTimeMillis();
        Long startTime = (Long) request.getAttribute(START_TIME);
        LOG.info("total time for request " + request.getRequestURL() + " is " + (endTime - startTime) + " ms");
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
        if (companyRepo.existsById(Long.parseLong(urlInfo[1]))) {
            companyId = Long.parseLong(urlInfo[1]);
            return true;
        }
        return false;
    }

}
