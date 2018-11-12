package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
public class EasyTester {

    @Test
    public void test(){
        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        System.out.println(Date.from(instant) instanceof Date);
    }
}
