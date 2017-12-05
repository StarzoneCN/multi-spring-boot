package com.example.demo.multi.springBoot.test.service.impl;


import com.example.demo.multi.springBoot.test.entity.Test;
import com.example.demo.multi.springBoot.test.mapper.TestMapper;
import com.example.demo.multi.springBoot.test.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: Li Hongxing
 * @description: Test表service实现类
 * @date: Created in 2017/12/02 0:33
 * @modified:
 */
@Service
public class TestServiceImpl implements TestService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private TestMapper testMapper;

    @Override
    public Test selectById(Integer id) {
        return testMapper.selectById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Test selectByContent(String content) {
        return testMapper.selectByContent(content);
    }

    @Override
    public int insert(Test record) {
        return testMapper.insert(record);
    }

    /**
     * MANDATORY策略：当事务回滚时会抛出IllegalTransactionStateException
     * REQUIRES_NEW策略：如果RE异常没有在调用方（方法）中捕获（try...catch...）调用方的事务也会被触发
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int subInsert(Test record){
        int count = testMapper.insert(record);
        if (count > 0) {
            Integer.parseInt("48fdsaf");
        }
        return 1;
    }
}
