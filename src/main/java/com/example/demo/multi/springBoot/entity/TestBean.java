package com.example.demo.multi.springBoot.entity;

import org.springframework.beans.factory.InitializingBean;

/**
 * 测试ioc容器bean相关的特性
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-10 17:18
 * @modefied:
 */
public class TestBean implements InitializingBean{

    private String property1;

    private void init() {
        System.out.println("------->> initial bean named TestBean");
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    private void destroy() {
        System.out.println("------->> destroy bean named TestBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("------->> initial bean named TestBean by method named \"afterPropertiesSet\"");
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "property1='" + property1 + '\'' +
                '}';
    }
}
