package com.itsz.netty.fast.protocol.service.impl;

import com.itsz.netty.fast.protocol.*;
import com.itsz.netty.fast.protocol.service.IHKStockPacket;
import com.itsz.netty.fast.protocol.service.IHKStockPacketFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class IHKStockPacketFactoryServiceImpl implements IHKStockPacketFactoryService {

	private static Logger logger = LoggerFactory.getLogger(IHKStockPacketFactoryServiceImpl.class);

	private Map<String, IHKStockPacket> beanFactory;

	private Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>();

	private IHKStockPacketFactoryServiceImpl() {
		beanFactory = new HashMap<String, IHKStockPacket>();
		clazzMap.put(HKStockPacketType.HEATBEAT.getType(),  HeatBeatPacket.class);
		clazzMap.put(HKStockPacketType.CALL_AUCTION.getType(), CallAuctionPacket.class);
		clazzMap.put(HKStockPacketType.SNAPSHOT.getType(), SnapshotPacket.class);
		clazzMap.put(HKStockPacketType.STOCK_DEFINITION.getType(), StockDefinitionPacket.class);
		clazzMap.put(HKStockPacketType.SNAPSHOT_SZ.getType(), SZSnapshotPacket.class);
	}

	@Override
	public IHKStockPacket createPacket(String type) {
		IHKStockPacket packet = null;
		try {
			packet = (IHKStockPacket) clazzMap.get(type).newInstance();
		} catch (Exception e) {
			//logger.error("create packet bean failed {}", e);
		}
		return packet;
	}

	@Override
	public IHKStockPacket getPacket(String type) {
		IHKStockPacket packet = beanFactory.get(type);
		if (packet == null) {
			packet = createPacket(type);
		}
		return packet;
	}
	
	public boolean contains(String msgType){
		return clazzMap.containsKey(msgType);
	}
	
	private static class SingletonHandler{
		private static IHKStockPacketFactoryServiceImpl packetFactory = new IHKStockPacketFactoryServiceImpl();
	}
	
	public static IHKStockPacketFactoryServiceImpl getInstance(){
		return SingletonHandler.packetFactory;
	}
}