package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.config.JedisConfig;
import com.example.demo.multi.springBoot.service.SimpleRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/19 17:03
 * @modefied:
 */
@Service
public class SimpleRedisServiceImpl implements SimpleRedisService {
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(SimpleRedisServiceImpl.class);

    // jedis 实例
    private Jedis jedis = null;
    /**
     * 分布式配置
     */
    @Autowired
    private JedisConfig jedisConfig;

    @Override
    public void destroy() throws Exception {
        if (jedis != null) {
            jedis.disconnect();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedis = new Jedis(jedisConfig.getHost(), jedisConfig.getPort()); //JedisUtil.createJedis(jedisConfig.getHost(),jedisConfig.getPort());
//        jedis.auth("");
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return
     */
    @Override
    public String getKey(String key) {
        if (jedis != null) {
            return jedis.get(key);
        }

        return null;
    }

    @Override
    public void changeJedis() {
        System.out.println("auto update sets - 自动载入更新配置。。。");
        try {
            destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jedis = new Jedis(jedisConfig.getHost(), jedisConfig.getPort());
    }

    @Override
    public Jedis getJedis() {
        return jedis;
    }
}
