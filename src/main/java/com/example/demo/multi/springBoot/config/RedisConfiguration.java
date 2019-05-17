package com.example.demo.multi.springBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;

/**
 * redis
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * ValueOperations：操作简单key-value类型数据
     */
    @Bean("stringValueOperations")
    public ValueOperations stringValueOperations(){
        return stringRedisTemplate.opsForValue();
    }

    /**
     * ZSetOperations：操作sortedSet类型数据
     */
    @Bean("stringZSetOperations")
    public ZSetOperations stringZSetOperations(){
        return stringRedisTemplate.opsForZSet();
    }

    /**
     * SetOperations：操作set类型数据
     * @return
     */
    @Bean("stringSetOperations")
    public SetOperations stringSetOperations(){
        return stringRedisTemplate.opsForSet();
    }

    /**
     * ListOperations：操作list类型数据
     * @return
     */
    @Bean("stringListOperations")
    public ListOperations stringListOperations(){
        return stringRedisTemplate.opsForList();
    }
}
