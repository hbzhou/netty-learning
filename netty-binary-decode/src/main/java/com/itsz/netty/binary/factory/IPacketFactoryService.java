package com.itsz.netty.binary.factory;


import com.itsz.netty.binary.IPacket;

public interface IPacketFactoryService extends IFactoryService<IPacket> {
	
	IPacket createPacket(Integer type);
      
	IPacket getPacket(Integer type);
      

}
