package com.example.demo.multi.springBoot.service;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/9/15 14:56:52
 */
public interface TransactionExperimentService {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void firstUpdateOne();

    @Transactional(isolation = Isolation.READ_COMMITTED)
    void secondUpdateOne();

    void methodOne();

    void methodTwo();

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    User getOneById(int id);

    void testRepeatedlyRead();
}
