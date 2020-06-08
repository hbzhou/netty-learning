package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

import java.math.BigDecimal;

public class IndexSnapshotPacket extends AbstractSZSnapshot implements IPacket {

	private BigDecimal lastPrice;

	private BigDecimal preClosePrice;

	private BigDecimal open;

	private BigDecimal high;

	private BigDecimal low;
	

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getPreClosePrice() {
		return preClosePrice;
	}

	public void setPreClosePrice(BigDecimal preClosePrice) {
		this.preClosePrice = preClosePrice;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
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

	@Override
	public void execute(ByteBuf in) {
		// 读取基本行情数据
		readBaseSnapshot(in);
		for (int i = 0; i < this.NoMDEntries; i++) {
			MDEntry entry = new MDEntry();
			entry.setEntryType(BinaryUtil.getStringData(in, 2));
			entry.setMDEntryPx(in.readLong());
			if(EnumEntryType.LAST_INDEX.getType().equals(entry.getEntryType().trim())){
				this.lastPrice = entry.getMDEntryPx();
			}
			if(EnumEntryType.PRECLOSE_INDEX.getType().equals(entry.getEntryType().trim())){
				this.preClosePrice = entry.getMDEntryPx();
			}
			if(EnumEntryType.OPEN_INDEX.getType().equals(entry.getEntryType().trim())){
				this.open = entry.getMDEntryPx();
			}
			if(EnumEntryType.HIGH_INDEX.getType().equals(entry.getEntryType().trim())){
				this.high = entry.getMDEntryPx();
			}
			if(EnumEntryType.LOW_INDEX.getType().equals(entry.getEntryType().trim())){
				this.low = entry.getMDEntryPx();
			}
			
		}

	}

	@Override
	public String toString() {
		return "IndexSnapshotPacket [lastPrice=" + lastPrice + ", preClosePrice=" + preClosePrice + ", open=" + open
				+ ", high=" + high + ", low=" + low + ", origTime=" + origTime + ", channelNo=" + channelNo
				+ ", MDStreamID=" + MDStreamID + ", securityId=" + securityId + ", securityIDSource=" + securityIDSource
				+ ", tradingPhaseCode=" + tradingPhaseCode + ", prevClosePx=" + prevClosePx + ", numTrades=" + numTrades
				+ ", totalVolumeTrade=" + totalVolumeTrade + ", totalValueTrade=" + totalValueTrade + ", NoMDEntries="
				+ NoMDEntries + "]";
	}

	@Override
	public Object[] convert2DBFRow() {
		Object[] data = new Object[36];
		data[0] = this.securityId;
		data[1] = "";
		data[2] = this.prevClosePx==null?0:prevClosePx;
		data[3] = this.open==null?0:open;
		data[4] = this.lastPrice==null?0:lastPrice;
		data[5] = this.totalValueTrade;
		data[6] = this.totalVolumeTrade;
		data[7] = this.numTrades;
		data[8] = this.high==null?0:high;
		data[9] = this.low==null?0:low;
		data[14] = 0;
		data[35] = Long.valueOf((this.origTime+"").substring(8, 14));
		return data;

	}
	
	

}
