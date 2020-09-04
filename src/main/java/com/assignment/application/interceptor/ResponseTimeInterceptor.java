package com.assignment.application.interceptor;

import com.assignment.application.constants.StringConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ResponseTimeInterceptor extends HandlerInterceptorAdapter {

    final static Logger LOG = LogManager.getLogger(ResponseTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute(StringConstant.START_TIME, System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        long startTime = (long) request.getAttribute(StringConstant.START_TIME);
        LOG.info("Time taken by " + request.getRequestURL() + " is " + (endTime - startTime) + " ms");
        super.postHandle(request, response, handler, modelAndView);
    }
}
