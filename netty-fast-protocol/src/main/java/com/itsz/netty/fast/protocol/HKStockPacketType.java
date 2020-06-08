package com.itsz.netty.fast.protocol;

public enum HKStockPacketType {
	
	HEATBEAT("UA1202","心跳消息"),
	CALL_AUCTION("UA2207","集合竞价"),
	SNAPSHOT("UA2202","行情快照"),
	STOCK_DEFINITION("UA2221","证券定义"),
	SNAPSHOT_SZ("300111","证券定义"),
	;
	
	private String type;
	
	private String desc;
	
	
	private HKStockPacketType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	

}
