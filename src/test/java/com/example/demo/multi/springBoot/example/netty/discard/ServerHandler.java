package com.example.demo.multi.springBoot.example.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/8/14 13:45:15
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handle has been removed");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Discard the received data silently.
        ByteBuf m = (ByteBuf)msg;
        m.clear();
        m.writeBytes("ServerHandler.hashCode = ".getBytes())
                .writeBytes(Integer.toString(this.hashCode()).getBytes())
                .writeInt(9)
                .writeBytes("ctx.hashCode = ".getBytes())
                .writeBytes(Integer.toString(ctx.hashCode()).getBytes())
                .writeInt(13).writeInt(10);
        ctx.writeAndFlush(m);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
