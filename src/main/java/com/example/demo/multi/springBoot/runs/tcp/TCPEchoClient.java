package com.example.demo.multi.springBoot.runs.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/13 17:47
 * @modefied:
 */
public class TCPEchoClient {

    public static void main(String[] args) throws IOException {
        String server = "::1";
        byte[] data = "Hello server, this is client!呼叫，这里是业务部 。。。".getBytes("utf8");
        int port = 80;
        Socket socket = new Socket(server, port);
        System.out.println("Connected to server ...");

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        os.write(data);
        int totalRcvd = 0;
        int byteRcvd;
        while (totalRcvd < data.length) {
            if ((byteRcvd = is.read(data, totalRcvd, data.length - totalRcvd)) == -1) {
                throw new SocketException("Connection is closed ... ");
            }
            totalRcvd += byteRcvd;
        }
        System.out.println("Recieved: " + new String(data, "utf8"));
        socket.close();
    }
}
