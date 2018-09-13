package com.example.demo.multi.springBoot.boot.client;

import com.example.demo.multi.springBoot.processor.client.IntegerClientProcessor;
import com.example.demo.multi.springBoot.protocol.IntegerProtocol;
import org.smartboot.socket.transport.AioQuickClient;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/9/13 19:21
 * @modefied:
 */
public class IntegerClient {

    public static void main(String[] args) throws Exception {
        IntegerClientProcessor processor = new IntegerClientProcessor();
        AioQuickClient<Integer> aioQuickClient = new AioQuickClient<Integer>("localhost", 8888, new IntegerProtocol(), processor);
        aioQuickClient.start();
        processor.getSession().write(1);
        Thread.sleep(1000);
        aioQuickClient.shutdown();
    }
}
