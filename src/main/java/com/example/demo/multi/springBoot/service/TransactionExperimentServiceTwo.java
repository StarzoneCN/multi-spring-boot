package com.example.demo.multi.springBoot.service;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/9/15 14:56:52
 */
public interface TransactionExperimentServiceTwo {
    void methodOne();


    void methodTwo();

    void methodThree();

    User getOneById(int id);
}
