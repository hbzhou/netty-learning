package com.itsz.netty.fast.protocol.snapshot;

public class BidBrokerQueue {

	private int bidItem;
	
	private String bidType; //B 表示 BidItem 值为经纪人 ID S 表示 BidItem 值为差价

	public int getBidItem() {
		return bidItem;
	}

	public void setBidItem(int bidItem) {
		this.bidItem = bidItem;
	}

	public String getBidType() {
		return bidType;
	}

	public void setBidType(String bidType) {
		this.bidType = bidType;
	}

	@Override
	public String toString() {
		return "BidBrokerQueue [bidItem=" + bidItem + ", bidType=" + bidType + "]";
	}
	
	
	
}
