package com.example.demo.multi.springBoot.controller;

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

   @RequestMapping
    public String index() {
        return "Conquer the world from here!";
    }
}
