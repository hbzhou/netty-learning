package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PostSnapshotPacket extends AbstractSZSnapshot implements IPacket {

	/** 五档委托买量  // 五档 委买价格 */
	
	private List<Long> buy5List = new LinkedList<Long>();
	
	private List<BigDecimal> buy5PriceList = new LinkedList<BigDecimal>();
	
	/** 五档委托卖量  // 五档 委卖价格 */
	private List<Long> sell5List = new LinkedList<Long>();
	
	private List<BigDecimal> sell5PriceList = new LinkedList<BigDecimal>();


	public List<Long> getBuy5List() {
		return buy5List;
	}

	public void setBuy5List(List<Long> buy5List) {
		this.buy5List = buy5List;
	}

	public List<BigDecimal> getBuy5PriceList() {
		return buy5PriceList;
	}

	public void setBuy5PriceList(List<BigDecimal> buy5PriceList) {
		this.buy5PriceList = buy5PriceList;
	}

	public List<BigDecimal> getSell5PriceList() {
		return sell5PriceList;
	}

	public void setSell5PriceList(List<BigDecimal> sell5PriceList) {
		this.sell5PriceList = sell5PriceList;
	}

	public List<Long> getSell5List() {
		return sell5List;
	}

	public void setSell5List(List<Long> sell5List) {
		this.sell5List = sell5List;
	}


	@Override
	public void execute(ByteBuf in) {
		readBaseSnapshot(in);
		for (int i = 0; i < NoMDEntries; i++) {
			MDEntry entry = new MDEntry();
			entry.setEntryType(BinaryUtil.getStringData(in, 2));
			entry.setMDEntryPx(in.readLong());
			entry.setMDEntrySize(in.readLong());
			if (EnumEntryType.BUY.getType().equals(entry.getEntryType().trim())) {
				buy5List.add(entry.getMDEntrySize());
				buy5PriceList.add(entry.getMDEntryPx());
			}
			if (EnumEntryType.SELL.getType().equals(entry.getEntryType().trim())) {
				buy5List.add(entry.getMDEntrySize());
				buy5PriceList.add(entry.getMDEntryPx());
			}

		}
	}

	@Override
	public String toString() {
		return "PostSnapshotPacket [buy5List=" + buy5List + ", buy5PriceList=" + buy5PriceList + ", sell5List="
				+ sell5List + ", sell5PriceList=" + sell5PriceList + ", origTime=" + origTime + ", channelNo="
				+ channelNo + ", MDStreamID=" + MDStreamID + ", securityId=" + securityId + ", securityIDSource="
				+ securityIDSource + ", tradingPhaseCode=" + tradingPhaseCode + ", prevClosePx=" + prevClosePx
				+ ", numTrades=" + numTrades + ", totalVolumeTrade=" + totalVolumeTrade + ", totalValueTrade="
				+ totalValueTrade + ", NoMDEntries=" + NoMDEntries + "]";
	}

	@Override
	public Object[] convert2DBFRow() {
		Object[] data = new Object[36];
		data[0] = this.securityId;
		data[1] = "";
		data[2] = this.prevClosePx==null?0:prevClosePx;
		data[5] = this.totalValueTrade;
		data[6] = this.totalVolumeTrade;
		data[7] = this.numTrades;
		data[14] = 0;
		Iterator<BigDecimal> sell5Priceiterator = sell5PriceList.iterator();
		Iterator<Long> sell5iterator = sell5List.iterator();
		if(sell5Priceiterator.hasNext()){
			data[15] = sell5Priceiterator.next();
		}else{
			data[15] = 0;
		}
		if(sell5iterator.hasNext()){
			data[16] = sell5iterator.next();
		}else{
			data[16] = 0;
		}
		
		if(sell5Priceiterator.hasNext()){
			data[17] = sell5Priceiterator.next();
		}else{
			data[17] = 0;
		}
		if(sell5iterator.hasNext()){
			data[18] = sell5iterator.next();
		}else{
			data[18] = 0;
		}
		if(sell5Priceiterator.hasNext()){
			data[19] = sell5Priceiterator.next();
		}else{
			data[19] = 0;
		}
		if(sell5iterator.hasNext()){
			data[20] = sell5iterator.next();
		}else{
			data[20] = 0;
		}
		
		if(sell5Priceiterator.hasNext()){
			data[21] = sell5Priceiterator.next();
		}else{
			data[21] = 0;
		}
		if(sell5iterator.hasNext()){
			data[22] = sell5iterator.next();
		}else{
			data[22] = 0;
		}

		if(sell5Priceiterator.hasNext()){
			data[23] = sell5Priceiterator.next();
		}else{
			data[23] = 0;
		}
		if(sell5iterator.hasNext()){
			data[24] = sell5iterator.next();
		}else{
			data[24] = 0;
		}
		
		Iterator<BigDecimal> buy5Priceiterator = buy5PriceList.iterator();
		Iterator<Long> buy5iterator = buy5List.iterator();
		if(buy5Priceiterator.hasNext()){
			data[25] = buy5Priceiterator.next();
		}else{
			data[25] = 0;
		}
		if(buy5iterator.hasNext()){
			data[26] = buy5iterator.next();
		}else{
			data[26] = 0;
		}
		
		if(buy5Priceiterator.hasNext()){
			data[27] = buy5Priceiterator.next();
		}else{
			data[27] = 0;
		}
		if(buy5iterator.hasNext()){
			data[28] = buy5iterator.next();
		}else{
			data[28] = 0;
		}
		
		if(buy5Priceiterator.hasNext()){
			data[29] = buy5Priceiterator.next();
		}else{
			data[29] = 0;
		}
		if(buy5iterator.hasNext()){
			data[30] = buy5iterator.next();
		}else{
			data[30] = 0;
		}
		if(buy5Priceiterator.hasNext()){
			data[31] = buy5Priceiterator.next();
		}else{
			data[31] = 0;
		}
		if(buy5iterator.hasNext()){
			data[32] = buy5iterator.next();
		}else{
			data[32] = 0;
		}
		if(buy5Priceiterator.hasNext()){
			data[33] = buy5Priceiterator.next();
		}else{
			data[33] = 0;
		}
		if(buy5iterator.hasNext()){
			data[34] = buy5iterator.next();
		}else{
			data[34] = 0;
		}
		data[35] = Long.valueOf((this.origTime+"").substring(8, 14));
		return data;
	}

	
	
}
