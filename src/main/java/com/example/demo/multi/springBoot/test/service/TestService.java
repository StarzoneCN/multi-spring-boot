package com.example.demo.multi.springBoot.test.service;

import com.example.demo.multi.springBoot.test.entity.Test;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;

public interface TestService {

    Test selectById(Integer id);

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    Test selectByContent(String content);

    int insert(Test record);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    int subInsert(Test record) throws NamingException;
}
