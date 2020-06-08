package com.itsz.netty.rpc.client;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class ClientChannelHandler extends SimpleChannelInboundHandler<String> implements Callable<String> {

    private String param;

    private Channel channel;

    private String result;


    @Override
    public synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        result = msg;
        notify();
    }

    @Override
    public synchronized String call() throws Exception {
        channel.writeAndFlush(param);
        wait();
        return this.result;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
