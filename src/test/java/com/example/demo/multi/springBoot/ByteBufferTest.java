package com.example.demo.multi.springBoot;

import org.apache.tomcat.util.buf.ByteBufferUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Enumeration;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/11 9:23
 * @modefied:
 */
public class ByteBufferTest {

    @Test
    public void test() throws UnsupportedEncodingException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put(("我爱你，中国！").getBytes("utf8"));
        System.out.println(byteBuffer.position());
        byteBuffer.flip();
        byte[]   bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
    }

    @Test
    public void test1() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces =  NetworkInterface.getNetworkInterfaces();
        if (networkInterfaces == null) {
            System.out.println("--No netWorkInterfaces found-- ");
        } else {
            while (networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                System.out.println("networkInterface " + networkInterface.getName() + ":");
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                if (!inetAddresses.hasMoreElements()){
                    System.out.println("\t (No address for this interface)");
                }
                while (inetAddresses.hasMoreElements()){
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println("\tInetAddress" + (inetAddress instanceof Inet4Address ? "(IPv4)" :
                            (inetAddress instanceof Inet6Address ? "(IPv6)" : "(?)")) + "is " + (inetAddress.isLoopbackAddress()? "" : "not ") + " a Loopback address");
                }
            }
        }
    }

    @Test
    public void test2() throws UnknownHostException, SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByName("lo");
        System.out.println("fdsa");
    }
}
