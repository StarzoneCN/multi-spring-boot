package com.example.demo.multi.springBoot.websocket;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;

/**
 * 编程式websocket服务端
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/8 20:17
 * @modefied:
 */
public class ProgrammaticEchoServer extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        final Session s = session;
        s.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String message) {
                try {
                    s.getBasicRemote().sendText("I got this(" + message + ") so I am sending it back!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
