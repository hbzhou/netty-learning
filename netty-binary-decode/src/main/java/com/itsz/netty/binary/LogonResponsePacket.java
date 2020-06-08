package com.itsz.netty.binary;


import io.netty.buffer.ByteBuf;

public class LogonResponsePacket implements IPacket{
	
	
	@Override
	public void execute(ByteBuf byteBuf) {
		BinaryUtil.getStringData(byteBuf, BinaryUtil.LEN_SENDER);
		BinaryUtil.getStringData(byteBuf, BinaryUtil.LEN_RECEVIER);
		byteBuf.readInt();
		BinaryUtil.getStringData(byteBuf, BinaryUtil.LEN_PASSWORD);
		BinaryUtil.getStringData(byteBuf, BinaryUtil.LEN_DEFAULTAPPLVERID);
		byteBuf.readInt();
	}

	@Override
	public Object[] convert2DBFRow() {
		return new Object[36];
	}

	@Override
	public String getSecurityId() {
		return "";
	}

}
