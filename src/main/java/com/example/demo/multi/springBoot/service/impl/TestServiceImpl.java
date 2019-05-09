package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.TestService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "prototype")
public class TestServiceImpl implements TestService {

    @Override
    public void test(){
        System.out.println("[" + this.hashCode() + "]run TestServiceImpl.test()");
    }
}
