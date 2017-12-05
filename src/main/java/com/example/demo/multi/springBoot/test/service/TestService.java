package com.example.demo.multi.springBoot.test.service;

import com.example.demo.multi.springBoot.test.entity.Test;

public interface TestService {

    Test selectById(Integer id);

    int insert(Test record);

    int update(Test record);
}
