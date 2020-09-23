package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.service.TransactionExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * spring事务测试
 *
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/9/15 14:52:37
 */
@RestController
@RequestMapping("transTest")
public class TransactionExperimentController {

    @Autowired
    private TransactionExperimentService transactionExperimentService;

    @GetMapping
    public String index(){
        transactionExperimentService.methodOne();
        return "over";
    }

    @GetMapping("user/{id}")
    public User userById(@PathVariable Integer id){
        return transactionExperimentService.getOneById(id);
    }

    @GetMapping("trr")
    public String testRepeatedlyRead(){
        transactionExperimentService.testRepeatedlyRead();
        return "over";
    }

    @GetMapping("first")
    public String firstUpdate(){
        transactionExperimentService.firstUpdateOne();
        return "over";
    }

    @GetMapping("second")
    public String secondUpdate(){
        transactionExperimentService.secondUpdateOne();
        return "over";
    }
}
