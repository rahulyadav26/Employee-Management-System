package com.assignment.application.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOG = LogManager.getLogger(ResponseLogging.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = httpServletRequest;
        HttpServletResponse response = httpServletResponse;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        byte[] requestArray = requestWrapper.getContentAsByteArray();
        String requestBody = new String(requestArray, requestWrapper.getCharacterEncoding());
        LOG.info("Request Body " + requestBody);
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseArray, responseWrapper.getCharacterEncoding());
        LOG.info("Response Body " + responseBody);
        responseWrapper.copyBodyToResponse();
    }
}
