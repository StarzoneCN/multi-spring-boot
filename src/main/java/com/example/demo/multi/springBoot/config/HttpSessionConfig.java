package com.example.demo.multi.springBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/17 21:21
 * @modefied:
 */
@EnableRedisHttpSession
@Configuration
public class HttpSessionConfig {
}
