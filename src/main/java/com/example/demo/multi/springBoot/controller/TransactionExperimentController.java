package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.service.TransactionExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("m2")
    public String m2(){
        transactionExperimentService.methodTwo();
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

    @GetMapping("getByName/{name}")
    public List<User> getByName(@PathVariable String name){
        return transactionExperimentService.getByName(name);
    }

    @PatchMapping("updateById")
    public String updateById(@RequestBody User user){
        transactionExperimentService.updateById(user);
        return "success";
    }
}
