package com.example.demo.multi.springBoot.example.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/9/25 15:04:43
 */
public class ClientEchoHandler extends ChannelInboundHandlerAdapter {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = br.readLine();
        System.out.println("send：" + message);
        ctx.writeAndFlush(Unpooled.buffer().writeBytes(message.getBytes(StandardCharsets.UTF_8)));
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf)msg;
        byte[] req = new byte[bb.readableBytes()];
        bb.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        System.out.println("receive from server: " + body);

        String msgToSent = br.readLine();
        bb.clear();
        bb.writeBytes(msgToSent.getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
