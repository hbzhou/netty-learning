package com.itsz.netty.protobuf;

import com.itsz.netty.protobuf.dto.MyDataInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        MyDataInfo.MyData myData = MyDataInfo.MyData.newBuilder().setDataTypeValue(MyDataInfo.MyData.DataType.studentType_VALUE).setStudent(MyDataInfo.Student.newBuilder().setId(1).setAge(30).setName("protobuf").build()).build();

        ctx.channel().writeAndFlush(myData);

    }
}
