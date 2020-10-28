package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
public class EasyTester {

    @Test
    public void test(){
        System.out.println(System.currentTimeMillis());
    }

    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;
            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }
            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("Client1", f).start();
        new Client("Client2", f).start();
        System.out.println("waiting");
        //设置Future.get()获取到的值
        f.complete(100);
        //以异常的形式触发计算
        //f.completeExceptionally(new Exception());
        Thread.sleep(1000);
    }
}
