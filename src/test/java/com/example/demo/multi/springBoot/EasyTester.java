package com.example.demo.multi.springBoot;

import lombok.Data;
import org.junit.Test;

import java.text.ParseException;
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
    public void test() {
        String reg = "\\d(\\d)(?<id>\\d)";
        String str = "123abc456";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);


        System.out.println("matcher matches = "+matcher.find());
        System.out.println("no parameter = "+matcher.group());
        System.out.println("number parameter = "+matcher.group(1));
        System.out.println("name parameter = "+matcher.group("id"));
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

    public static void main(String[] args) {
        String s3 = "1" + new String("1");
        String s5 = s3.intern();
        System.out.println(s5 == s3);
    }
}
