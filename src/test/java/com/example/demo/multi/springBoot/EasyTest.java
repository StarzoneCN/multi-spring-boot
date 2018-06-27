package com.example.demo.multi.springBoot;

import cn.hutool.json.JSONUtil;
import org.junit.Test;
import sun.applet.Main;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/30 10:52
 * @modefied:
 */
public class EasyTest {

    @Test
    public void test() {
        float t = 3.99f;
        int integration = 200;

        int tAnd100 = (int)(t * 100);
        int n = tAnd100 / 2;
        int y = tAnd100 % 2;

        System.out.print("You need to pay : " );
        if (n > integration || n == integration && y > 0){
            System.out.println(t - integration * 0.02);
        } else {
            System.out.println(t - (integration -1) * 0.02);
        }
    }

    public static void main(String[] args) {
        float t = 5f;
        int integration = 200;

        int tAnd100 = (int)(t * 100);
        int n = tAnd100 / integration;
        int y = tAnd100 % integration;
        if (n > integration || n == integration && y > 0){
            System.out.println((t - integration * 0.02));
        } else {
            System.out.println((t - (integration -1) * 0.02));
        }
    }
}
