package com.itsz.netty.binary.factory;

import com.itsz.netty.binary.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class IPacketFactoryServiceImpl implements IPacketFactoryService {

	private static Logger logger = LoggerFactory.getLogger(IPacketFactoryServiceImpl.class);

	private Map<Integer, IPacket> beanFactory;

	private Map<Integer, Class<?>> clazzMap = new HashMap<Integer, Class<?>>();

	private IPacketFactoryServiceImpl() {
		beanFactory = new HashMap<Integer, IPacket>();
		clazzMap.put(EnumPacketType.HEARTBEAT.getType(), HeartBeatPacket.class);
		clazzMap.put(EnumPacketType.HEARTBEAT_SNAPSHOT.getType(),HeartBeatPacket.class);
		clazzMap.put(EnumPacketType.SNAPSHOT.getType(), SnapshotPacket.class);
		clazzMap.put(EnumPacketType.POST_SNAPSHOT.getType(), PostSnapshotPacket.class);
		clazzMap.put(EnumPacketType.INDEX_SNAPSHOT.getType(),IndexSnapshotPacket.class);
	}

	@Override
	public IPacket createPacket(Integer type) {
		IPacket packet = null;
		try {
			packet = (IPacket) clazzMap.get(type).newInstance();
		} catch (Exception e) {
			logger.error("create packet bean failed {}", e);
		}
		return packet;
	}

	@Override
	public IPacket getPacket(Integer type) {
		IPacket packet = beanFactory.get(type);
		if (packet == null) {
			packet = createPacket(type);
		}
		return packet;
	}
	
	public boolean contains(Integer msgType){
		return clazzMap.containsKey(msgType);
	}
	
	private static class SingletonHandler{
		private static IPacketFactoryServiceImpl packetFactory = new IPacketFactoryServiceImpl();
	}
	
	public static IPacketFactoryServiceImpl getInstance(){
		return SingletonHandler.packetFactory;
	}
}