package com.example.demo.multi.springBoot.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;

public interface SimpleRedisService extends InitializingBean, DisposableBean {
    String getKey(String key);

    void changeJedis();

    Jedis getJedis();
}
