package com.example.gateway.config;

import com.example.gateway.service.JwtAuthFilter;
import com.example.gateway.service.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class JwtAuthConfig {

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtil jwtUtil) {
        return new JwtAuthFilter(jwtUtil);
    }

    @Bean(name = "jwtAuthFilterFilterRegistrationBean")
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterFilterRegistrationBean(JwtAuthFilter jwtAuthFilter) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>(jwtAuthFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/*"); // Add this line to apply the filter to all requests
        return registrationBean;
    }
}