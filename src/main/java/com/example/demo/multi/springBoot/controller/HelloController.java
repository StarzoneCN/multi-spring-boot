package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.service.SimpleService;
import com.example.demo.multi.springBoot.service.impl.SimpleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Li Hongxing
 * @description: Hello world！
 * @date: Created in 2017/12/05 12:13
 * @modified:
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @Autowired
    private SimpleService simpleService;

   @RequestMapping
    public String index() {
       simpleService.print();
        return "Let's go ! Conquer the world from here!";
    }
}
