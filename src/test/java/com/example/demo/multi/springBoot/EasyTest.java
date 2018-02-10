package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.entity.TestBean;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 不启动SpringBoot
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-10 17:29
 * @modefied:
 */
public class EasyTest {

    @Test
    public void testBean() {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("static/beans.xml");
        if (cpxac.containsBean("testBean")) {
            TestBean tb = cpxac.getBean("testBean", TestBean.class);
            System.out.println(tb);
        }
    }
}
