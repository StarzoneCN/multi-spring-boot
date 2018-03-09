package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.annotation.RequestLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LiHongxing
 * @date: Create in 2018-03-09 9:01
 * @modefied:
 */
@RestController("index")
public class IndexController {

    @RequestLog("execute index mathod ... ")
    @RequestMapping
    public String index() {
        return "Welcome to spring world!";
    }
}
