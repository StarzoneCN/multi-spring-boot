package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.service.LoopInjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 14:20:20
 */
public class LoopInjectServiceTest {

    @Autowired
    private LoopInjectService loopInjectService;

    @Test
    public void test(){
        loopInjectService.doPrint();
    }
}
