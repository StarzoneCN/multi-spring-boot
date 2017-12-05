package com.example.demo.multi.springBoot.test.mapper;

import com.example.demo.multi.springBoot.test.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {

    Test selectById(@Param("id") Integer id);

    Test selectByContent(@Param("content") String content);

    int insert(Test record);
}
