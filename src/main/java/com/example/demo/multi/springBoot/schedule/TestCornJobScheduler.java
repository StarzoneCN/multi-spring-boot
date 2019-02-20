package com.example.demo.multi.springBoot.schedule;

import com.example.demo.multi.springBoot.schedule.jobs.TestJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/19 23:51
 * @modefied by
 */
public class TestCornJobScheduler {

    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder
                .newJob(TestJob.class)
                .withIdentity("jobName", "jobGroup")  //定义job的名字和组
                .usingJobData("name", "yanjun")  //传参数，key:name value:yanjun
                .usingJobData("age", 18)  //传参数，key:age value:18
                .build();

        String cronExpression = "0/10 * * 20C * ?";

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
