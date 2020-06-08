package com.itsz.netty.fast.protocol.service;

import io.netty.buffer.ByteBuf;

public interface IHKStockPacket {
	
	void execute(ByteBuf in, int len);
	
	

}
