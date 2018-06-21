package com.example.demo.multi.springBoot;

import org.junit.Test;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
public class EasyTester {

    @Test
    public void test() {

    }

    public final int value = 4;

    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;

            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String... args) {
        EasyTester m = new EasyTester();
        m.doIt();
    }
}
