package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.test.entity.Test;
import com.example.demo.multi.springBoot.test.service.TestService;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Integer insert(Test test){
        System.out.println("-----------------before insert-----------------");
        int successInsertCount = testService.insert(test);
        if (successInsertCount > 0) {
            /*当抛出RuntimeException时，事务回滚*/
            throw new  IndexOutOfBoundsException();
        }
        return 1;
    }
}
