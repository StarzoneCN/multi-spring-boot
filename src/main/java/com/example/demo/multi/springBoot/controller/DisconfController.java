package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.config.JedisConfig;
import com.example.demo.multi.springBoot.service.impl.SimpleRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/19 17:12
 * @modefied:
 */
@RestController
@RequestMapping("disconf")
public class DisconfController {
    private static final String REDIS_KEY = "disconf_key";

    @Autowired
    private SimpleRedisServiceImpl simpleRedisService;
    @Autowired
    private JedisConfig jedisConfig;

    @RequestMapping("sv")
    public String showValue() {
        String message = "redis( " + jedisConfig.getHost() + "," + jedisConfig.getPort() + ")  get key: " + REDIS_KEY;
        return message;
    }
}
