package com.itsz.netty.binary;


import com.itsz.netty.binary.dbf.componet.DBFComponet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class SnapshotHandler extends SimpleChannelInboundHandler<IPacket> {

	private static Logger logger = LoggerFactory.getLogger(SnapshotHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IPacket msg) throws Exception {
		logger.info("receive snapshot from server msg {}",msg);
    	//写入DBF 
		if(msg instanceof SnapshotPacket){
			DBFComponet.writeDBF(msg);
		}
	}

}
