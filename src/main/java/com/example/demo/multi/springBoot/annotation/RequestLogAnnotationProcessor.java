package com.example.demo.multi.springBoot.annotation;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: LiHongxing
 * @date: Create in 2018-03-09 8:54
 * @modefied:
 */
@Aspect
@Component
public class RequestLogAnnotationProcessor {

    @Pointcut("@annotation(com.example.demo.multi.springBoot.annotation.RequestLog)")
    public void pointcut() {}

    @Before("pointcut() && @annotation(requestLog)")
    public void before(JoinPoint joinPoint, RequestLog requestLog) {
        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.S") + " : " + requestLog.value());
    }
}
