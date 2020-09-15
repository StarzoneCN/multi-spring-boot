package com.example.demo.multi.springBoot.example.nio.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-07-10 13:40:15
 * @modefied:
 */
public class NIOClient {
    SocketChannel channel;

    public void initClient(String host, int port) throws IOException {
        InetSocketAddress servAddr = new InetSocketAddress(host, port);
        this.channel = SocketChannel.open(servAddr);
    }

    public void sendAndRecv(String words) throws IOException {
        byte[] msg = new String(words).getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(msg);
        System.out.println("sending: " + new String(buffer.array()).trim());
        channel.write(buffer);
        buffer.clear();
        System.out.println("清理后: " + new String(buffer.array()).trim());
        channel.read(buffer);
        System.out.println("received: " + new String(buffer.array()).trim());

        buffer.clear();
        System.out.println("sending: " + "again");
        buffer = ByteBuffer.wrap(new String("again").getBytes());
        channel.write(buffer);
        buffer.clear();
        channel.read(buffer);
        System.out.println("received: " + new String(buffer.array()).trim());
        buffer.clear();
        channel.close();
    }

    public static void main(String[] args) throws IOException {
        NIOClient client = new NIOClient();
        client.initClient("localhost", 7788);
        client.sendAndRecv("this is client");
    }
}
