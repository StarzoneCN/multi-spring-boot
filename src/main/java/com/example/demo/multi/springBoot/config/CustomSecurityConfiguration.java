package com.example.demo.multi.springBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/17 17:29
 * @modefied:
 */
// @Configuration
// @EnableWebSecurity
public class CustomSecurityConfiguration {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionFixation().none();
    }
}
