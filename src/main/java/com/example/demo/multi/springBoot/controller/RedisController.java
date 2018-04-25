package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.service.SimpleRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/20 17:16
 * @modefied:
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private SimpleRedisService simpleRedisService;

    @RequestMapping("get")
    public Map<String, Object> getValue(String key) {
        Map<String, Object> map = new HashMap<>();
        Jedis jedis = simpleRedisService.getJedis();
        map.put("jedis.hash", jedis.hashCode());
        map.put("jedis.info", jedis.info());
        String  valueOfKey = simpleRedisService.getKey(key);
        map.put(valueOfKey, valueOfKey);
        return map;
    }
}
