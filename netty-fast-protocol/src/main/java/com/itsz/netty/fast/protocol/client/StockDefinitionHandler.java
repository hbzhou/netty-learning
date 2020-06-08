package com.itsz.netty.fast.protocol.client;


import com.itsz.netty.fast.protocol.StockDefinitionPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class StockDefinitionHandler extends SimpleChannelInboundHandler<StockDefinitionPacket> {

	private static Logger log = LoggerFactory.getLogger(StockDefinitionHandler.class);


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		log.error(e.getMessage(), e);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, StockDefinitionPacket msg) throws Exception {
		log.info("receiver snapshot from VDE  msg {}", msg);
	}
}
