package com.example.demo.multi.springBoot.schedule.jobs;

import org.quartz.*;

import java.util.Date;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/19 23:16
 * @modefied by
 */
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("SimpleJob：任务执行了: ");
    }
}
