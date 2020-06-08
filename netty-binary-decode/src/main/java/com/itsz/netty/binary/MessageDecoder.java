package com.itsz.netty.binary;

import com.itsz.netty.binary.factory.IPacketFactoryServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

	private static final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
	
	private IPacketFactoryServiceImpl factory = IPacketFactoryServiceImpl.getInstance();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		int readableByteslen = in.readableBytes();
		if (readableByteslen < BinaryUtil.HEADLEN) {
			in.resetReaderIndex();
			return;
		}
		int msgType = in.readInt();
		int bodyLen = in.readInt();
		if (in.readableBytes() < bodyLen+4) {
			in.resetReaderIndex();
			return;
		}
		if(!factory.contains(msgType)){
			in.readBytes(bodyLen+4);
			in.discardReadBytes();
			return;
		}
		IPacket packet = factory.createPacket(msgType);
		packet.execute(in);
		in.readInt();
		in.discardReadBytes();
		out.add(packet);
	}

}
