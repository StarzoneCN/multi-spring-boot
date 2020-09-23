package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
public class EasyTester {

    @Test
    public void test(){
        System.out.println(new AtomicInteger().getAndIncrement());
    }
}
