package com.itsz.netty.group.chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

public class NettyCLientHandler extends SimpleChannelInboundHandler<String > {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String mssage) throws Exception {
        System.out.println(mssage);
    }
}
