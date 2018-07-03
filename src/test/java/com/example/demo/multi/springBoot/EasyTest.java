package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.util.*;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/9 11:13
 * @modefied:
 */
public class EasyTest {

    @Test
    public void test() {
        Map<String, String> map =  new HashMap<>();
        for (String s : map.keySet()) {
            
        }
    }

    @Test
    public void testPriorityQueue() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(null);
        list.add(2);
        PriorityQueue pq = new PriorityQueue();
        pq.addAll(list);
        System.out.println(pq.toString());
    }

    public static void main(String[] args) {
        String str = "12345678";
        System.out.println(str.substring(str.length()-2));
    }
}