package com.itsz.netty.fast.protocol.snapshot;

public class BidPriceLevel {

	private int bidSize; //申买量
	
	private int bidPx; //申买价
	
	private int bidOrderNumbers; //经纪人数量

	public int getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public int getBidPx() {
		return bidPx;
	}

	public void setBidPx(int bidPx) {
		this.bidPx = bidPx;
	}

	public int getBidOrderNumbers() {
		return bidOrderNumbers;
	}

	public void setBidOrderNumbers(int bidOrderNumbers) {
		this.bidOrderNumbers = bidOrderNumbers;
	}

	@Override
	public String toString() {
		return "BidPriceLevel [bidSize=" + bidSize + ", bidPx=" + bidPx + ", bidOrderNumbers=" + bidOrderNumbers + "]";
	}
	
	
	
}
