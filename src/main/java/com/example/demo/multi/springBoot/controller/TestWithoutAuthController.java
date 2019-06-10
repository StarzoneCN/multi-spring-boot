package com.example.demo.multi.springBoot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestWithoutAuthController {

    @GetMapping
    public String index(){
        return "success";
    }
}
