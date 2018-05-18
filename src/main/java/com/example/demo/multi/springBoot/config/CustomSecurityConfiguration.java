package com.example.demo.multi.springBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/17 17:29
 * @modefied:
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionFixation().none();
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().permitAll().and()
                .rememberMe()
                .key("starzoneCN")
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("warplaneInLaji")
                .tokenValiditySeconds(60 * 60).and()
                .httpBasic();
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("USER");

    }
}
