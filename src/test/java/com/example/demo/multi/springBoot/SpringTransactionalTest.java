package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/30 8:48:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTransactionalTest {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void insertUser(){
        User u = new User().setName("test_junit");
        userService.save(u);
    }
}
