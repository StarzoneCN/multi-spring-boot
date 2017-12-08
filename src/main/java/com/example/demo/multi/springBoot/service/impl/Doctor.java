package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.Job;
import org.springframework.stereotype.Service;

/**
 * @author: Li Hongxing
 * @description: 职业：医生
 * @date: Created in 2017/12/08 10:32
 * @modified:
 */
@Service
public class Doctor implements Job {

    public static final String JOB_NAME = "Doctor";

    @Override
    public String showJobName() {
        return JOB_NAME;
    }
}
