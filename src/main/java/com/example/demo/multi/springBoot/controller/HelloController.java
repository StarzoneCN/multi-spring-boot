package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.BeanExample;
import com.example.demo.multi.springBoot.service.Job;
import com.example.demo.multi.springBoot.util.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: Li Hongxing
 * @description: Hello world！
 * @date: Created in 2017/12/05 12:13
 * @modified:
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @RequestMapping
    public String index() {
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        BeanExample beanExample = applicationContext.getBean("beanExample", BeanExample.class);
        System.out.println("----beanExample----> " + beanExample.toString());

        Map<String, Job> jobs = applicationContext.getBeansOfType(Job.class);
        System.out.println("----jobs----> " + jobs.toString());
        return "Conquer the world from here!";
    }
}
