package com.itsz.netty.fast.protocol;


import com.itsz.netty.fast.protocol.service.IHKStockPacket;

public abstract class AbstractPacket implements IHKStockPacket {
	
	protected String content;
	
	protected int rawDataLen;

	protected byte[] rawData;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	@Override
	public String toString() {
		return "packet [content=" + content + "]";
	}
	
	
	
	

}
