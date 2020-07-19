package com.assignment.application.logging;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseLogging extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = httpServletRequest;
        HttpServletResponse response = httpServletResponse;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper,responseWrapper);
        byte[] requestArray = requestWrapper.getContentAsByteArray();
        String requestBody = new String(requestArray,requestWrapper.getCharacterEncoding());
        System.out.println("Request Body: " + requestBody);
        byte[] responseArray= responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseArray,responseWrapper.getCharacterEncoding());
        System.out.println("Response Body: " + responseBody);
        responseWrapper.copyBodyToResponse();
    }
}
