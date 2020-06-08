package com.itsz.netty.fast.protocol.client;

import com.itsz.netty.fast.protocol.service.IHKStockPacket;
import com.itsz.netty.fast.protocol.service.impl.IHKStockPacketFactoryServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

	private static final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
	
	private static IHKStockPacketFactoryServiceImpl factory = IHKStockPacketFactoryServiceImpl.getInstance();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		in.markReaderIndex();
		int len = in.readableBytes();
		if (len < StepUtil.minHeaderSize) {
			in.resetReaderIndex();
			return;
		}
		byte[] constHeader = new byte[StepUtil.stepVersionHeaderSize];
		in.readBytes(constHeader);
		String constHeaderStr = new String(constHeader, "UTF-8");
		if (!StepUtil.stepVersionHeader.equals(constHeaderStr)) {
			in.resetReaderIndex();
			return;
		}
		int pos = in.bytesBefore((byte) StepUtil.STEP_SOH);
		if (pos < 0) {
			in.resetReaderIndex();
			return;
		}
		ByteBuf buf = in.readBytes(pos);
		in.readByte();
		byte[] valueBytes = new byte[pos];
		buf.readBytes(valueBytes);
		String[] bodyLenvalues = new String(valueBytes).split("=");
		int bodyLen = Integer.parseInt(bodyLenvalues[1]);
		if (bodyLen + StepUtil.constTrailerSize > in.readableBytes()) {
			in.resetReaderIndex();
			return;
		}
		int msgTypePos = in.bytesBefore((byte) StepUtil.STEP_SOH);
		if(msgTypePos<0){
			in.resetReaderIndex();
			return;
		}
		ByteBuf bufNew = in.readBytes(msgTypePos);
		in.readByte();
		byte[] msgTypeBytes = new byte[msgTypePos];
		bufNew.readBytes(msgTypeBytes);
		String[] msgTypeValues = new String(msgTypeBytes).split("=");
		String msgType = msgTypeValues[1];
		logger.info("receive packet  msgType {}",msgType);
		if ("A".equals(msgType)) {
			logger.info("receive logon response");
			in.readBytes(new byte[in.readableBytes()]);
			return;
		}
		int restLen = bodyLen - msgTypePos - 1;
		if (!factory.contains(msgType)) {
			in.readBytes(new byte[restLen+StepUtil.constTrailerSize]);
			return;
		}
		if(msgType.equals("300111")){
			logger.info("receive sz snapshot packet  msgType {}",msgType);
		}
		logger.info("receive packet  msgType {}",msgType);
		IHKStockPacket packet = factory.createPacket(msgType);
		packet.execute(in,restLen);
		out.add(packet);
	}

}
