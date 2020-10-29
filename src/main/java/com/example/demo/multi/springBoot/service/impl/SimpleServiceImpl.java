package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.LoopInjectService;
import com.example.demo.multi.springBoot.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-07-25 14:25:24
 * @modefied:
 */
@Component
public class SimpleServiceImpl implements SimpleService {
    @Autowired
    private LoopInjectService loopInjectService;

    @Override
    public void print() {
        loopInjectService.doPrint();
        System.out.println("SimpleServiceImpl - 打印了");
    }

    @Override
    public String someMessage(){
        return "message";
    }

    @Override
    public String timeString(){
        return LocalDateTime.now().toString();
    }
}
