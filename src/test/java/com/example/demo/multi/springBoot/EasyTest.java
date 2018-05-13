package com.example.demo.multi.springBoot;

import org.junit.Test;
import org.springframework.util.NumberUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/13 17:59
 * @modefied:
 */
public class EasyTest {

    @Test
    public void test() {
        System.out.println(((((255 << 8)| 255) << 8)|255) << 8 | 255);
        System.out.println(Integer.toBinaryString(-2147483648));
    }
}
