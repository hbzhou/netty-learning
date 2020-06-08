package com.itsz.netty.rpc.server;

import com.itsz.netty.rpc.client.RpcConsumerServer;
import com.itsz.netty.rpc.server.service.impl.HelloWorldServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        if (message.startsWith(RpcConsumerServer.PROVIDER_NAME)) {
            String result = new HelloWorldServiceImpl().sayHello(message.substring(message.lastIndexOf("#") + 1));
            channelHandlerContext.writeAndFlush(result);
        }
    }
}
