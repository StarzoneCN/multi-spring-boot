package com.example.demo.multi.springBoot.example.nio.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-07-10 13:39:09
 * @modefied:
 */
public class NIOServer {

    private Selector selector;
    ServerSocketChannel serverSocketChannel;

    public void initServer(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", port));
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        System.out.println("server started succeed!");
        while (true) {
            selector.select();
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                if(key.isAcceptable()) {
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if(key.isReadable()) {
                    recvAndReply(key);
                }
                ite.remove();
            }
        }
    }

    public void recvAndReply(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(13);
        int i = channel.read(buffer);
        System.out.println("字节流长度：" + i);
        if (i != -1) { //用于判断客户端是否断开了连接
            String msg = new String(buffer.array()).trim();
            System.out.println("server received message: " + msg);
            msg = "received：" + msg;
            System.out.println("server reply: " + msg);
            channel.write(ByteBuffer.wrap(msg.getBytes()));
        } else {
            channel.close(); //如果客户端断开连接就关闭该连接
        }
    }

    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.initServer(7788);
        server.listen();
    }
}
