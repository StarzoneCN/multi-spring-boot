package com.example.demo.multi.springBoot.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-07-25 15:37:48
 * @modefied:
 */
@Aspect
@Component
public class AopTest {


    @Pointcut("execution(* com.example.demo.multi.springBoot.service.impl.SimpleServiceImpl.*())")
    public void pc() {
    }

    @Before("pc()")
    public void doSomething() {
        System.out.println("before 中执行。。。");
    }
}
