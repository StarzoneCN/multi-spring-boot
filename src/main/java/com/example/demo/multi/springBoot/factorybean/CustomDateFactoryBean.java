package com.example.demo.multi.springBoot.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 时间factorybean
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-10 19:32
 * @modefied:
 */
@Component
public class CustomDateFactoryBean implements FactoryBean<Date> {

    @PostConstruct
    public void init() {
        System.out.println("------- >> 这是初始化方法");
    }
    @Override
    public Date getObject() throws Exception {
        return new Date();
    }

    @Override
    public Class<?> getObjectType() {
        return Date.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
