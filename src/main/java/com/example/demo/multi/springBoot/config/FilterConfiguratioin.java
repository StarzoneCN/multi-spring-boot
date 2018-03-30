package com.example.demo.multi.springBoot.config;

import com.example.demo.multi.springBoot.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: LiHongxing
 * @date: Create in 2018-02-05 10:28
 * @modefied:
 */
@Configuration
public class FilterConfiguratioin {

    @Autowired private TokenFilter tokenFilter;

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //添加过滤器
        registration.setFilter(tokenFilter);
        //设置过滤路径，/*所有路径
        registration.addUrlPatterns("*");
        registration.setName("tokenFilter");
        return registration;
    }
}
