package com.example.demo.multi.springBoot.runs.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/13 20:26
 * @modefied:
 */
public class UDPEchoServer {
    private static final int MAX_LEGTH = 255;

    public static void main(String[] args) {
        int port = 81;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            DatagramPacket packet = new DatagramPacket(new byte[MAX_LEGTH], MAX_LEGTH);
            while (true){
                try {
                    socket.receive(packet);
                    System.out.println("收到来自客户端(" + packet.getAddress().getHostName()+ ":" + packet.getPort() + ")的消息:" + new String(packet.getData()));
                    socket.send(packet);
                    packet.setLength(MAX_LEGTH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
