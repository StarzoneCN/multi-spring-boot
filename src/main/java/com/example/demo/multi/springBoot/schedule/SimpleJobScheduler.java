package com.example.demo.multi.springBoot.schedule;

import com.example.demo.multi.springBoot.schedule.jobs.SimpleJob;
import com.example.demo.multi.springBoot.schedule.jobs.TestJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/19 23:18
 * @modefied by
 */
public class SimpleJobScheduler {

    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("jobName", "jobGroup")  //定义job的名字和组
                .build();
        String cronExpression = "0/3 * * ? * 4c";

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", "triggerGroup")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronExpression)
                )
                .build();

        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start(); //开始执行
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
