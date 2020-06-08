package com.itsz.netty.protobuf;

import com.itsz.netty.protobuf.dto.Stduent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

public class ProtoBufHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Stduent.Student student = Stduent.Student.newBuilder().setId(1).setName("Jeremy").setAge(32).build();
        ctx.channel().writeAndFlush(student);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }
}
