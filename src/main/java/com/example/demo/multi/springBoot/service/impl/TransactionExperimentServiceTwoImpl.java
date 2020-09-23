package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
import com.example.demo.multi.springBoot.service.TransactionExperimentServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/9/15 14:57:07
 */
@Service
public class TransactionExperimentServiceTwoImpl implements TransactionExperimentServiceTwo {
    @Autowired
    private UserService userService;

    @Override
    public void methodOne(){
        User u = new User().setName("service2-methodOne").setAge(1);
        userService.save(u);

       /* TransactionExperimentService transactionExperimentService =
                applicationContext.getBean(TransactionExperimentService.class);*/
        methodTwo();
        methodThree();
    }

    @Override
    @Transactional
    public void methodTwo(){
        System.out.println("service2-methodOne：" + TransactionSynchronizationManager.getCurrentTransactionName());
        User u = new User().setName("service2-methodTwo").setAge(1);
        userService.save(u);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public void methodThree(){
        User u = new User().setName("service2-methodThree").setAge(1);
        userService.save(u);
        throw new RuntimeException();
    }

    @Override
//    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User getOneById(int id){
        return userService.getById(id);
    }
}
