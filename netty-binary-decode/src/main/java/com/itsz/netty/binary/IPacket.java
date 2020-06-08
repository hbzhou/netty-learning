package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

public interface IPacket {
	
	 void execute(ByteBuf byteBuf);
	 
	 Object[] convert2DBFRow();
	 
	 String getSecurityId();

}
