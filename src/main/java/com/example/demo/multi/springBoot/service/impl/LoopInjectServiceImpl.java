package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.LoopInjectService;
import com.example.demo.multi.springBoot.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/28 13:36:57
 */
@Service
public class LoopInjectServiceImpl implements LoopInjectService {

    @Autowired
    private SimpleService simpleService;

    @Override
    public void doPrint(){
        System.out.println("do print ：" + simpleService.someMessage());
    }
}
