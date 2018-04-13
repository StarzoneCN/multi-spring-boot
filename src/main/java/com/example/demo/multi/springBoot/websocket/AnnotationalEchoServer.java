package com.example.demo.multi.springBoot.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
import javax.xml.ws.WebEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * websocket注解式实现
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/8 22:53
 * @modefied:
 */
@Component
@ServerEndpoint("/echoserver/echo")
public class AnnotationalEchoServer {

    @OnMessage
    public String echo(String message){
        return "I got this (" + message + ") so I am sending it back!";
    }
}
