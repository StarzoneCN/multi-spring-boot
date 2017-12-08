package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.Job;
import org.springframework.stereotype.Service;

/**
 * @author: Li Hongxing
 * @description: 职业：程序员
 * @date: Created in 2017/12/08 10:33
 * @modified:
 */
@Service
public class Programmer implements Job {

    public static final String JOB_NAME = "Programmer";

    @Override
    public String showJobName() {
        return JOB_NAME;
    }
}
