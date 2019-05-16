package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.constant.ResponseCodeEnum;
import com.example.demo.multi.springBoot.service.TestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
@Component
public class EasyTester {

    @Test
    public void test(){

    }

    @Test
    public void mockId(){
        String region = "420621";
        String year = "1992";
        String month = "12";
        String day = "24";
        String randomNum = String.valueOf((int)(Math.random() * 999));
        int[] factors = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int i = 0;
        int sum = 0;
        for (char c : (region + year + month + day + randomNum).toCharArray()) {
            sum += ((int)c - 48) * factors[i];
            i++;
        }
        int tail = (12 - sum % 11) % 11;
        if (tail != 10){
            System.out.println(region + year + month + day + randomNum + tail);
        } else {
            System.out.println(region + year + month + day + randomNum + "X");
        }
    }

    @Test
    public void bitchMockId(){
        int i = 10;
        for (int j=0; j<i; j++) {
            mockId();
        }
    }
}
