package com.example.demo.multi.springBoot.config;

import com.example.demo.multi.springBoot.filter.AuthFilter;
import com.example.demo.multi.springBoot.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustemConfigurerAdapter {
    @Autowired
    private AuthFilter authFilter;
    @Value("authFilter.urls")
    private List<String> filteUrls;

    @Bean
    public FilterRegistrationBean authFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("authFilter");
        registrationBean.setFilter(authFilter);
        registrationBean.setOrder(1);
        registrationBean.setUrlPatterns(filteUrls);
        return registrationBean;
    }
}
