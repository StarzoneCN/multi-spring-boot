package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/13 16:21
 * @modefied:
 */
public class ServerSocketChannelTest {

    @Test
    public void simpleServerSocketChannelTest() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8888));
//        ssc.configureBlocking(false);
        String hello_string = "hello rudy!";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
//            System.out.println("wait for connections");
            SocketChannel clientSocket = ssc.accept();
            if (null == clientSocket){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println(String.format("incomimg connection from: %s",clientSocket.getRemoteAddress()));
                StringBuilder sb = new StringBuilder();
                while (clientSocket.read(buffer) != -1) {
                    sb.append(new String(buffer.array()));
                    if (buffer.position() < buffer.capacity())
                        break;
                }
                System.out.println(sb.toString());

                buffer.rewind();
                clientSocket.write(buffer);
                clientSocket.close();
            }
        }
    }
}
