package com.itsz.netty.binary;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeatHandler extends SimpleChannelInboundHandler<HeartBeatPacket> {
	private static Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatPacket msg) throws Exception {
		logger.info("receive heartbeat from server msg {}",msg);
			ByteBuf byteBuf = Unpooled.buffer();
	    	byteBuf.writeInt(BinaryUtil.HEART_BEAT_MSG_TYPE);
	    	byteBuf.writeInt(0);
	    	byteBuf.writeInt(BinaryUtil.HEART_BEAT_MSG_TYPE);
	    	ctx.writeAndFlush(byteBuf);
		}
		

}
