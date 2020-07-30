package com.assignment.application.authenticator;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Company;
import com.assignment.application.exception.AuthenticationException;
import com.assignment.application.exception.UnauthorisedException;
import com.assignment.application.repo.CompanyRepo;
import com.assignment.application.repo.EmployeeRepo;
import com.assignment.application.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyUsers {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private RedisService redisService;

    public int authorizeUser(String token,String url,String type){
        String cachedInfo = redisService.getKeyValue(StringConstant.ACCESS_TOKEN_REGEX + token);
        if(cachedInfo.indexOf("roles: superadmin")==-1){
            String[] info = token.split("-");
            String[] urlSplit = url.split("/");
            Company company = companyRepo.findById(Long.parseLong(info[info.length-1])).orElse(null);
            String companyName = company.getName();
            String employeeId = companyName.toLowerCase() + "_" + info[0];
            if(urlSplit.length==3 && (urlSplit[2].equalsIgnoreCase("salary") || urlSplit[2].equalsIgnoreCase("update-employee-info")) &&
                    (type.equalsIgnoreCase("GET") || type.equalsIgnoreCase("PATCH"))){
                if(urlSplit[1].equalsIgnoreCase(employeeId) && info[info.length-1].equals(urlSplit[0])){
                    return 1;
                }
                throw new UnauthorisedException("Not allowed to access");
            }
            throw new UnauthorisedException("Not allowed to access");
        }
        return 1;
    }
}
