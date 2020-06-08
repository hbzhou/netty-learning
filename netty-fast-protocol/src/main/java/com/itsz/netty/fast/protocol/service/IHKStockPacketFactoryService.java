package com.itsz.netty.fast.protocol.service;

public interface IHKStockPacketFactoryService extends IFactoryService<IHKStockPacket> {
	
	IHKStockPacket createPacket(String type);
      
	IHKStockPacket getPacket(String type);
      

}
