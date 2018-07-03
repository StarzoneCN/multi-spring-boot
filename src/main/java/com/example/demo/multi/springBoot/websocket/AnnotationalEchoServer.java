package com.example.demo.multi.springBoot.websocket;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.ws.WebEndpoint;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
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
@ServerEndpoint(value = "/echoserver/echo", encoders = MessageDecoder.class)
public class AnnotationalEchoServer {
    private static final String HOST = "192.168.16.254";
    private static final int PORT = 8001;

    private Socket socket;
    private Session session;

    @OnMessage
    public String echo(String message) {
        return "I got this (" + message + ") so I am sending it back!";
    }

    @OnOpen
    public void onOpen(Session session) throws InterruptedException {
        this.session = session;

        System.out.println("连接电子秤...");
        while (true) {
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                if (ObjectUtils.isEmpty(socket)) {
                    socket = new Socket(HOST, PORT);
                    System.out.println(socket.isConnected() ? "连接成功" : "连接失败");;
                }
                Thread.sleep(100);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                int size = input.available();
                byte[] resp = new byte[size];
                input.read(resp);
                String response = new String(resp, "utf-8");
                if (!StringUtils.isEmpty(response)) {
                    String reverse = new StringBuffer(response.substring(1)).reverse().toString();
                    sendMessage(reverse);
                }
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            System.out.println("===============发送了消息:" + message);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("连接电子秤...");
        Socket socket = null;
        while (true) {
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                if (ObjectUtils.isEmpty(socket)) {
                    socket = new Socket(HOST, PORT);
                }

                //读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                int size = input.available();
                byte[] resp = new byte[size];
                input.read(resp);
                String response = new String(resp, "utf-8");
                if (!StringUtils.isEmpty(response)) {
                    System.out.println(response);
                }
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
            }
        }
    }
}
