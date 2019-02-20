package com.example.demo.multi.springBoot.schedule.jobs;

import org.quartz.*;

import java.util.Date;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/19 23:16
 * @modefied by
 */
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任务执行了: ");

        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String name = dataMap.getString("name");  //获取名字
        Integer age = dataMap.getInt("age");    //获取年龄

        System.out.println("name: " + name + "  age:" + age);

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String jobName = jobDetail.getKey().getName();
        String jobGroup = jobDetail.getKey().getGroup();

        System.out.println("jobName: " + jobName + "  jobGroup:" + jobGroup);

        Trigger trigger = jobExecutionContext.getTrigger();
        String triggerName = trigger.getKey().getName();
        String triggerGroup = trigger.getKey().getGroup();
        Date startTime = trigger.getStartTime();  //获取任务开始时间
        Date endTime = trigger.getEndTime();    //获取任务结束时间

        System.out.println("triggerName: " + triggerName + "  triggerGroup:" + triggerGroup);
    }
}
