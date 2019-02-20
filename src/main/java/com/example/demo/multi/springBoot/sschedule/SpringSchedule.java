package com.example.demo.multi.springBoot.sschedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * spring schedule
 *
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/20 11:04
 * @modefied by
 */
@Component
public class SpringSchedule {

    @Scheduled(cron = "0/2 * * * * ?")
    public void simpleTest(){
        System.out.println("执行定时。。。。");
    }
}
