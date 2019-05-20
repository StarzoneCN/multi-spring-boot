package com.example.demo.multi.springBoot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description: 日期时间工具类
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2019-05-20 10:47:33
 * @modefied:
 **/
public class DateTimeUtils {
    public static final String FMT_DATE_TIME_GENERAL = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_GENERAL = "yyyy-MM-dd";
    private DateTimeUtils(){}

    public static String dateTimeFormat(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern(FMT_DATE_TIME_GENERAL).format(localDateTime);
    }

    public static String dateTimeFormat(LocalDateTime localDateTime, String pattern){
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    public static String dateFormat(LocalDate localDate){
        return DateTimeFormatter.ofPattern(FMT_DATE_GENERAL).format(localDate);
    }

    public static String dateFormat(LocalDate localDate, String pattern){
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }
}
