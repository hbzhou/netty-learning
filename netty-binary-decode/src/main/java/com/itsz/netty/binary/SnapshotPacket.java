package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public  class SnapshotPacket extends AbstractSZSnapshot implements IPacket {
	
	private BigDecimal high;

	private BigDecimal low;

	private BigDecimal lastPrice;

	private BigDecimal open;

	private BigDecimal PERatio1; // 市盈率1

	private BigDecimal PERatio2 ;// 市盈率2

	private BigDecimal limitUpPrice ; // 涨停价

	private BigDecimal limitDownPrice; // 跌停价
	
	private BigDecimal RF1; //升跌一
	
	private BigDecimal RF2; //升跌二
	
	
	
	/** 五档委托买量  // 五档 委买价格 */
	
	private List<Long> buy5List = new LinkedList<Long>();
	
	private List<BigDecimal> buy5PriceList = new LinkedList<BigDecimal>();
	
	/** 五档委托卖量  // 五档 委卖价格 */
	private List<Long> sell5List = new LinkedList<Long>();
	
	private List<BigDecimal> sell5PriceList = new LinkedList<BigDecimal>();
	

	

	
	/*protected long b1;
	protected long b2;
	protected long b3;
	protected long b4;
	protected long b5;*/

/*	
	protected double b1Price;
	protected double b2Price;
	protected double b3Price;
	protected double b4Price;
	protected double b5Price;*/
	// 委卖5
	/*protected long s1;
	protected long s2;
	protected long s3;
	protected long s4;
	protected long s5;

	protected double s1Price;
	protected double s2Price;
	protected double s3Price;
	protected double s4Price;
	protected double s5Price;
*/

	public List<BigDecimal> getBuy5PriceList() {
		return buy5PriceList;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getPERatio1() {
		return PERatio1;
	}

	public void setPERatio1(BigDecimal pERatio1) {
		PERatio1 = pERatio1;
	}

	public BigDecimal getPERatio2() {
		return PERatio2;
	}

	public void setPERatio2(BigDecimal pERatio2) {
		PERatio2 = pERatio2;
	}

	public BigDecimal getLimitUpPrice() {
		return limitUpPrice;
	}

	public void setLimitUpPrice(BigDecimal limitUpPrice) {
		this.limitUpPrice = limitUpPrice;
	}

	public BigDecimal getLimitDownPrice() {
		return limitDownPrice;
	}

	public void setLimitDownPrice(BigDecimal limitDownPrice) {
		this.limitDownPrice = limitDownPrice;
	}

	public BigDecimal getRF1() {
		return RF1;
	}

	public void setRF1(BigDecimal rF1) {
		RF1 = rF1;
	}

	public BigDecimal getRF2() {
		return RF2;
	}

	public void setRF2(BigDecimal rF2) {
		RF2 = rF2;
	}

	public List<Long> getBuy5List() {
		return buy5List;
	}

	public void setBuy5List(List<Long> buy5List) {
		this.buy5List = buy5List;
	}

	public void setBuy5PriceList(List<BigDecimal> buy5PriceList) {
		this.buy5PriceList = buy5PriceList;
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


	public List<BigDecimal> getSell5PriceList() {
		return sell5PriceList;
	}

	@Override
	public void execute(ByteBuf in) {
		//读取基本行情资料
		readBaseSnapshot(in);
		for (int i = 0; i < NoMDEntries; i++) {
			MDEntry entry = new MDEntry();
			entry.setEntryType(BinaryUtil.getStringData(in, 2));
			entry.setMDEntryPx( in.readLong());
			entry.setMDEntrySize(in.readLong());
			entry.setMDPriceLevel(in.readShort());
			entry.setNumberOfOrders(in.readLong());
			int orderNo = in.readInt();
			for (int j = 0; j < orderNo; j++) {
				entry.addOrderQty(in.readLong());
			}
			if(EnumEntryType.BUY.getType().equals(entry.getEntryType().trim())){
				 buy5List.add(entry.getMDEntrySize());
				 buy5PriceList.add(entry.getMDEntryPx());
			}
			if(EnumEntryType.SELL.getType().equals(entry.getEntryType().trim())){
				 sell5List.add(entry.getMDEntrySize());
				 sell5PriceList.add(entry.getMDEntryPx());
			}
			if(EnumEntryType.HIGH.getType().equals(entry.getEntryType().trim())){
				this.high = entry.getMDEntryPx();
			}
			
			if(EnumEntryType.LASTPRICE.getType().equals(entry.getEntryType().trim())){
				this.lastPrice = entry.getMDEntryPx();
			}
			if(EnumEntryType.LOW.getType().equals(entry.getEntryType().trim())){
				this.low = entry.getMDEntryPx();
			}
			if(EnumEntryType.OPEN.getType().equals(entry.getEntryType().trim())){
				this.open = entry.getMDEntryPx();
			}
			if(EnumEntryType.PE1.getType().equals(entry.getEntryType().trim())){
				this.PERatio1 = entry.getMDEntryPx();
			}
			if(EnumEntryType.PE2.getType().equals(entry.getEntryType().trim())){
				this.PERatio2 = entry.getMDEntryPx();
			}
			if(EnumEntryType.UPLIMITPRICE.getType().equals(entry.getEntryType().trim())){
				this.limitUpPrice = entry.getMDEntryPx();
			}
			if(EnumEntryType.UPDOWNPRICE.getType().equals(entry.getEntryType().trim())){
				this.limitDownPrice = entry.getMDEntryPx();
			}
			if(EnumEntryType.RF1.getType().equals(entry.getEntryType().trim())){
				this.RF1 = entry.getMDEntryPx();
			}
			if(EnumEntryType.RF2.getType().equals(entry.getEntryType().trim())){
				this.RF2 = entry.getMDEntryPx();
			}
			
		}
		
	}



	@Override
	public String toString() {
		return "SnapshotPacket [high=" + high + ", low=" + low + ", lastPrice=" + lastPrice + ", open=" + open
				+ ", PERatio1=" + PERatio1 + ", PERatio2=" + PERatio2 + ", limitUpPrice=" + limitUpPrice
				+ ", limitDownPrice=" + limitDownPrice + ", RF1=" + RF1 + ", RF2=" + RF2 + ", buy5List=" + buy5List
				+ ", buy5PriceList=" + buy5PriceList + ", sell5List=" + sell5List + ", sell5PriceList=" + sell5PriceList
				+ ", origTime=" + origTime + ", channelNo=" + channelNo + ", MDStreamID=" + MDStreamID + ", securityId="
				+ securityId + ", securityIDSource=" + securityIDSource + ", tradingPhaseCode=" + tradingPhaseCode
				+ ", prevClosePx=" + prevClosePx + ", numTrades=" + numTrades + ", totalVolumeTrade=" + totalVolumeTrade
				+ ", totalValueTrade=" + totalValueTrade + ", NoMDEntries=" + NoMDEntries + "]";
	}

	@Override
	public Object[] convert2DBFRow() {
		Object[] data = new Object[36];
		data[0] = this.securityId;
		data[1] = "";
		data[2] = this.prevClosePx;
		data[3] = this.open==null?0:open;
		data[4] = this.lastPrice==null?0:lastPrice;
		data[5] = this.totalValueTrade;
		data[6] = this.totalVolumeTrade;
		data[7] = this.numTrades;
		data[8] = this.high==null?0:high;
		data[9] = this.low==null?0:low;
		data[10] = this.PERatio1==null?0:PERatio1;
		data[11] = this.PERatio2==null?0:PERatio2;
		data[12] = this.RF1==null?0:RF1;
		data[13] = this.RF2==null?0:RF2;
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
			data[25]= 0;
		}
		if(buy5iterator.hasNext()){
			data[26] = buy5iterator.next();
		}else{
			data[26]= 0;
		}
		
		if(buy5Priceiterator.hasNext()){
			data[27] = buy5Priceiterator.next();
		}else{
			data[27]= 0;
		}
		if(buy5iterator.hasNext()){
			data[28] = buy5iterator.next();
		}else{
			data[28]= 0;
		}
		
		if(buy5Priceiterator.hasNext()){
			data[29] = buy5Priceiterator.next();
		}else{
			data[29]= 0;
		}
		if(buy5iterator.hasNext()){
			data[30] = buy5iterator.next();
		}else{
			data[30]= 0;
		}
		if(buy5Priceiterator.hasNext()){
			data[31] = buy5Priceiterator.next();
		}else{
			data[31]= 0;
		}
		if(buy5iterator.hasNext()){
			data[32] = buy5iterator.next();
		}else{
			data[32]= 0;
		}
		if(buy5Priceiterator.hasNext()){
			data[33] = buy5Priceiterator.next();
		}else{
			data[33]= 0;
		}
		if(buy5iterator.hasNext()){
			data[34] = buy5iterator.next();
		}else{
			data[34]= 0;
		}
		data[35] = Long.valueOf((this.origTime+"").substring(8, 14));
		return data;
	}
	
	
	

}
