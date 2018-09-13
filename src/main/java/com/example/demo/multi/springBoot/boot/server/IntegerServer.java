package com.example.demo.multi.springBoot.boot.server;

import com.example.demo.multi.springBoot.processor.server.IntegerServerProcessor;
import com.example.demo.multi.springBoot.protocol.IntegerProtocol;
import org.smartboot.socket.transport.AioQuickServer;

import java.io.IOException;

/**
 * integer启动
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/9/13 19:20
 * @modefied:
 */
public class IntegerServer {

    public static void main(String[] args) throws IOException {
        AioQuickServer<Integer> server = new AioQuickServer<Integer>(8888, new IntegerProtocol(), new IntegerServerProcessor());
        server.start();
    }
}
