package com.ziya.moneymanagement.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.log4jfilter")
public class Log4jFilterConfiguration {

    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "Response_Token";
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "Log4jMDCFilter.UUID";

    private String requestHeader;

    @Bean
    public FilterRegistrationBean<Log4jFilter> servletRegistrationBean() {
        final FilterRegistrationBean<Log4jFilter> registrationBean = new FilterRegistrationBean<Log4jFilter>();
        final Log4jFilter log4jMDCFilterFilter = new Log4jFilter(DEFAULT_RESPONSE_TOKEN_HEADER, DEFAULT_MDC_UUID_TOKEN_KEY, requestHeader);
        registrationBean.setFilter(log4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
