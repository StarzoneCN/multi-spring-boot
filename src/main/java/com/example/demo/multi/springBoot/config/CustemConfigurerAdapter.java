package com.example.demo.multi.springBoot.config;

import com.example.demo.multi.springBoot.config.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class CustemConfigurerAdapter {
    @Autowired
    private AuthFilter authFilter;
    @Value("${auth.filter.urls}")
    private List<String> urls;

    @Bean
    public FilterRegistrationBean authFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("authFilter");
        registrationBean.setFilter(authFilter);
        registrationBean.setOrder(1);
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}
