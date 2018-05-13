package com.example.demo.multi.springBoot.runs.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/13 18:33
 * @modefied:
 */
public class TCPEchoServer {
    private static final int BUFFER_SIZE = 32;

    public static void main(String[] args) throws IOException {
        int serverPort = 80;
        ServerSocket serverSocket = new ServerSocket(serverPort);

        int recieveMsgSize;
        byte[] msgBytes = new byte[BUFFER_SIZE];
        while (true){
            Socket socket = serverSocket.accept();
            SocketAddress socketAddress = socket.getRemoteSocketAddress();
            System.out.println("Handler client at " + socketAddress);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            while ((recieveMsgSize = is.read(msgBytes)) != -1){
                os.write(msgBytes);
            }
            socket.close();
        }
    }
}
