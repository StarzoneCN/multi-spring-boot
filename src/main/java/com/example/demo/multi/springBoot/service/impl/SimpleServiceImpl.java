package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.SimpleService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-07-25 14:25:24
 * @modefied:
 */
@Component
public class SimpleServiceImpl implements SimpleService {
    @Override
    public void print() {
        System.out.println("SimpleServiceImpl - 打印了");
        this.async("1");
        this.async("2");
    }

    @Override
    @Async
    public void async(String msg){
        int i = 6;
        while (i-- >= 1){
            System.out.println(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
