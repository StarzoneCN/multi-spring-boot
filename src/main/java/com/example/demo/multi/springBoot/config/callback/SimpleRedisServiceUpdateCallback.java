package com.example.demo.multi.springBoot.config.callback;

import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.example.demo.multi.springBoot.config.JedisConfig;
import com.example.demo.multi.springBoot.service.SimpleRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/23 11:19
 * @modefied:
 */
@Service
@Scope("singleton")
@DisconfUpdateService(classes = {JedisConfig.class}) //, itemKeys = {Coefficients.key}
public class SimpleRedisServiceUpdateCallback implements IDisconfUpdate {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleRedisServiceUpdateCallback.class);

    @Autowired
    private SimpleRedisService simpleRedisService;

    @Override
    public void reload() throws Exception {
        simpleRedisService.changeJedis();
    }
}
