package com.example.demo.multi.springBoot.test.service;

import com.example.demo.multi.springBoot.test.entity.Test;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface TestService {

    Test selectById(Integer id);

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    Test selectByContent(String content);

    int insert(Test record);

    int update(Test record);
}
