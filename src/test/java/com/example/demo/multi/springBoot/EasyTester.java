package com.example.demo.multi.springBoot;

import lombok.Data;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
@Data
public class EasyTester {

    private char a = '\u0ffa';
    private int b;

    @Test
    public void test() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(br.readLine());
    }

    @Test
    public void t2(){
        Pattern TAX_CODE_PATTERN = Pattern.compile("[\\d]-(\\d)");
        String str2 = "1-2%";
        Matcher matcher2 = TAX_CODE_PATTERN.matcher(str2);
        System.out.println(TAX_CODE_PATTERN.toString());
        System.out.println("matcher matches = "+matcher2.find());
        System.out.println(matcher2.group(1));
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

    public static void main(String[] args) throws IOException {
        System.out.println(Long.parseLong("432143.314"));
    }
}
