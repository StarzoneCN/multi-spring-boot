package com.example.demo.multi.springBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/12 18:49
 * @modefied:
 */
@RestController
public class IndexController {
    private static final String MSG_APP_WELCOME = "欢迎来到Spring-Boot的世界！";

    @RequestMapping
    public String index(){
        return MSG_APP_WELCOME;
    }
}
