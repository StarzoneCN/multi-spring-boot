package com.example.demo.multi.springBoot;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-06-14 20:44:40
 * @modefied:
 */
public class MainTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 2);
                    System.out.println("才结束。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("执行了吗？");
    }

    static class MyThread extends Thread {
        public void run() {
            Thread hello = new HelloThread();
            hello.start(); // 启动hello线程
            try {
                hello.join(); // 等待hello线程结束
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            }
            hello.interrupt();
        }
    }

    static class HelloThread extends Thread {
        public void run() {
            int n = 0;
            while (!isInterrupted()) {
                n++;
                System.out.println(n + " hello!" + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
