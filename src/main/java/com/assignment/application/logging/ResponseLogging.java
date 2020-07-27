package com.assignment.application.logging;

import com.assignment.application.kafka.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = httpServletRequest;
        HttpServletResponse response = httpServletResponse;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        byte[] requestArray = requestWrapper.getContentAsByteArray();
        String requestBody = new String(requestArray, requestWrapper.getCharacterEncoding());
        logger.info("Request Body " + requestBody);
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseArray, responseWrapper.getCharacterEncoding());
        logger.info("Response Body " + responseBody);
        responseWrapper.copyBodyToResponse();
    }
}
