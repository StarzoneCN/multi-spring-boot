package com.example.demo.multi.springBoot.runs.udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/13 19:50
 * @modefied:
 */
public class UDPEchoClientTimeout {
    private static final int TIMEOUT = 3000;
    private static final int MAX_TRIES = 5;

    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
        InetAddress inetAddress = InetAddress.getByName("localhost");
        int prot = 81;
        byte[] data = "祖国母亲，我爱你！".getBytes("utf8");

        try (DatagramSocket socket = new DatagramSocket();) {
            socket.setSoTimeout(TIMEOUT);
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, prot);
            DatagramPacket recievePacket = new DatagramPacket(new byte[data.length], data.length);

            int tries = 0;
            boolean recieveSuccess = false;
            do {
                try {
                    socket.send(datagramPacket);
                    socket.receive(recievePacket);
                    if (!recievePacket.getAddress().equals(inetAddress)){
                        throw new IOException("收到莫名数据包");
                    }
                    recieveSuccess = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    tries += 1;
                    System.out.println("Try again ... ");
                }
            }while (!recieveSuccess && tries < MAX_TRIES);

            if (recieveSuccess){
                System.out.println("Revieve: " + new String(recievePacket.getData()));
            } else {
                System.out.println("接收失败。。。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
