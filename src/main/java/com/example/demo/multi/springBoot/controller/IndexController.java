package com.example.demo.multi.springBoot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/11/21 23:05
 * @modefied:
 */
@RestController
public class IndexController {

    @GetMapping("/sayHello")
    private String sayHello(){
        System.out.println("Hello World");
        return "Hello World";
    }
}
