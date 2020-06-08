package com.itsz.netty.fast.protocol.snapshot;

public class OffBrokerQueue {
	
	private int offItem;
	
	private String offType; //B 表示 BidItem 值为经纪人 ID S 表示 BidItem 值为差价

	public int getOffItem() {
		return offItem;
	}

	public void setOffItem(int offItem) {
		this.offItem = offItem;
	}

	public String getOffType() {
		return offType;
	}

	public void setOffType(String offType) {
		this.offType = offType;
	}

	@Override
	public String toString() {
		return "OffBrokerQueue [offItem=" + offItem + ", offType=" + offType + "]";
	}
	
	
	

}
