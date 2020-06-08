package com.itsz.netty.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(Thread.currentThread().getName()+"handle client ");
        ByteBuf byteBuf = ((ByteBuf) msg);
        System.out.println("receive message from client:" + ctx.channel().remoteAddress() + "  msg:" + byteBuf.toString(CharsetUtil.UTF_8));

        ctx.channel().eventLoop().execute( ()->{
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {

            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("delay task",CharsetUtil.UTF_8));
        });

        ctx.channel().eventLoop().execute( ()->{
            try {
                Thread.sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("delay task 2", CharsetUtil.UTF_8));
        });

        ctx.channel().eventLoop().schedule( ()->{
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2, TimeUnit.SECONDS);
        System.out.println("read complete!!");

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Client", CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
