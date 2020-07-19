package com.assignment.application.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLogging {

    @Bean
    public CommonsRequestLoggingFilter logBody() {

        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(100000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("Request Body start: ");
        filter.setAfterMessageSuffix("Request Body Ends");
        return filter;

    }

}
