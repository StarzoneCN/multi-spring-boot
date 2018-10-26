package com.example.demo.multi.springBoot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * mybatis-plus测试类
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/10/26 15:50
 * @modefied:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusSampleTest {
    // @Autowired
    // @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    // private UserMapper userMapper;
    //
    // @Test
    // public void testSelect() {
    //     System.out.println(("----- selectAll method test ------"));
    //     List<User> userList = userMapper.selectList(null);
    //     Assert.assertEquals(5, userList.size());
    //     userList.forEach(System.out::println);
    // }
}
