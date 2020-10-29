package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.controller.HelloController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 14:19:08
 */
public class HelloControllerTest {


    @Autowired
    private HelloController helloController;

    @Test
    public void testIndex(){
        helloController.index();
    }
}
