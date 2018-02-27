package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.entity.TestBean;
import com.example.demo.multi.springBoot.factorybean.CustomDateFactoryBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 不启动SpringBoot
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-10 17:29
 * @modefied:
 */
public class EasyTest {
    private static DefaultListableBeanFactory beanFactory;

    @Before
    public void startUp() {
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("static/beans.xml"));
    }

    @Test
    public void testBean() {
        if (beanFactory.containsBean("testBean")) {
            TestBean tb = beanFactory.getBean("testBean", TestBean.class);
            System.out.println(tb);
        }
    }

    @Test
    public void testFactoryBean() {
        if (beanFactory.containsBean("customDateFactoryBean")) {
            CustomDateFactoryBean customDateFactoryBean = beanFactory.getBean("customDateFactoryBean", CustomDateFactoryBean.class);
            System.out.println(customDateFactoryBean instanceof CustomDateFactoryBean);
        }
    }

    @Test
    public void testOperator() {
        System.out.println(3 >> 1);
    }

    @Test
    public void testTheMid() {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("1", null);
        treeMap.put("2", null);
        Map.Entry<String, Object> entry = treeMap.higherEntry("1");
        entry.setValue(100);
        System.out.println(treeMap.toString());
    }
}