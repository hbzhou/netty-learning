package com.itsz.netty.fast.protocol.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<HeartBeatMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, HeartBeatMessage msg, ByteBuf out) throws Exception {
	        String heartBeat = msg.getHeartbeat();
	        out.writeBytes(heartBeat.getBytes());
	}

}
