package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.test.entity.Test;
import com.example.demo.multi.springBoot.test.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Li Hongxing
 * @description:
 * @date: Created in 2017/12/05 13:42
 * @modified:
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource private TestService testService;

    @RequestMapping("add")
    public Integer insert(Test test){
        return testService.insert(test);
    }
}
