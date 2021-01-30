package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.SimpleService;
import org.springframework.stereotype.Component;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-07-25 14:40:04
 * @modefied:
 */
@Component
public class SimpleServiceImpl2 implements SimpleService {

    @Override
    public void print() {
        System.out.println("SimpleServiceImpl2 - 打印了");
    }

    @Override
    public void async(String msg) {

    }
}
