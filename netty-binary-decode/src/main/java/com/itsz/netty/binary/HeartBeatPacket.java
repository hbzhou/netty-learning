package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

public class HeartBeatPacket implements IPacket {
	
	private short channelNo;
	
	private long applLastSeqNum;
	
	private boolean endOfChannel;
	
	public short getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(short channelNo) {
		this.channelNo = channelNo;
	}

	public long getApplLastSeqNum() {
		return applLastSeqNum;
	}

	public void setApplLastSeqNum(long applLastSeqNum) {
		this.applLastSeqNum = applLastSeqNum;
	}

	public boolean isEndOfChannel() {
		return endOfChannel;
	}

	public void setEndOfChannel(boolean endOfChannel) {
		this.endOfChannel = endOfChannel;
	}

	@Override
	public void execute(ByteBuf byteBuf) {
		if(byteBuf.readableBytes()>4){
			this.channelNo = byteBuf.readShort();
			this.applLastSeqNum = byteBuf.readLong();
			this.endOfChannel = byteBuf.readBoolean();
			byteBuf.readByte();
		}
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
