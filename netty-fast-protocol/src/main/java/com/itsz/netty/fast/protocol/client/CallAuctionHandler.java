package com.itsz.netty.fast.protocol.client;

import com.itsz.netty.fast.protocol.CallAuctionPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class CallAuctionHandler  extends SimpleChannelInboundHandler<CallAuctionPacket> {
      
	private static Logger log = LoggerFactory.getLogger(SnapshotHandler.class);

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CallAuctionPacket msg) throws Exception {
		log.info("receiver CallAuction from VDE  msg {}", msg);

		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		log.error(e.getMessage(), e);
		ctx.close();
	}

	

}
