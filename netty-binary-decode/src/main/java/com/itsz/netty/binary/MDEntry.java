package com.itsz.netty.binary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class MDEntry {
	
	private String entryType;
	private BigDecimal MDEntryPx;
	private long MDEntrySize ;
	private short MDPriceLevel;
	private long NumberOfOrders;
	private List<Long> OrderQty = new ArrayList<Long>();
	
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public BigDecimal getMDEntryPx() {
		return MDEntryPx;
	}
	public void setMDEntryPx(long mDEntryPx) {
		MDEntryPx = new BigDecimal(mDEntryPx).divide(new BigDecimal(1000000)).setScale(2,RoundingMode.HALF_UP);
	}
	public long getMDEntrySize() {
		return MDEntrySize;
	}
	public void setMDEntrySize(long mDEntrySize) {
		MDEntrySize = mDEntrySize/100;
	}
	public short getMDPriceLevel() {
		return MDPriceLevel;
	}
	public void setMDPriceLevel(short mDPriceLevel) {
		MDPriceLevel = mDPriceLevel;
	}
	public long getNumberOfOrders() {
		return NumberOfOrders;
	}
	public void setNumberOfOrders(long numberOfOrders) {
		NumberOfOrders = numberOfOrders;
	}
	public List<Long> getOrderQty() {
		return OrderQty;
	}
	public void setOrderQty(List<Long> orderQty) {
		OrderQty = orderQty;
	}
	
	public void addOrderQty(Long qty){
		this.OrderQty.add(qty);
	}
	
	

}
