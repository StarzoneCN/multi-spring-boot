package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
public class EasyTester {
    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();
    static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(1000);
    }
    static void ft(Integer init) {
        System.out.println("init :" + init);
    }

    @Test
    public void test(){
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(EasyTester::getMoreData);
        future.thenAccept(EasyTester::ft);
        System.out.println(future.join());
    }
}
