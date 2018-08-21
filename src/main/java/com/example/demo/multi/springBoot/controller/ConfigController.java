package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置入口
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/18 1:01
 * @modefied:
 */
@RestController
@RequestMapping("config")
public class ConfigController {
    @Autowired
    private RedisConfig redisConfig;

    @GetMapping("redis")
    public RedisConfig getRedisConfig(){
        return redisConfig;
    }
}
