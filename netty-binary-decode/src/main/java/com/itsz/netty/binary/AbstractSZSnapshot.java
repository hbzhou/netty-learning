package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractSZSnapshot implements IPacket {

	protected long origTime;
	protected short channelNo;
	protected String MDStreamID;
	protected String securityId;
	protected String securityIDSource;
	protected String tradingPhaseCode;
	protected BigDecimal prevClosePx;
	protected long numTrades = 0 ; // 成交笔数
	protected long totalVolumeTrade = 0; // 成交总量
	protected long totalValueTrade = 0; // 成交总金额
	protected int NoMDEntries;

	public long getOrigTime() {
		return origTime;
	}

	public void setOrigTime(long origTime) {
		this.origTime = origTime;
	}

	public short getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(short channelNo) {
		this.channelNo = channelNo;
	}

	public String getMDStreamID() {
		return MDStreamID;
	}

	public void setMDStreamID(String mDStreamID) {
		MDStreamID = mDStreamID;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getSecurityIDSource() {
		return securityIDSource;
	}

	public void setSecurityIDSource(String securityIDSource) {
		this.securityIDSource = securityIDSource;
	}

	public String getTradingPhaseCode() {
		return tradingPhaseCode;
	}

	public void setTradingPhaseCode(String tradingPhaseCode) {
		this.tradingPhaseCode = tradingPhaseCode;
	}

	public BigDecimal getPrevClosePx() {
		return prevClosePx;
	}

	public void setPrevClosePx(BigDecimal prevClosePx) {
		this.prevClosePx = prevClosePx;
	}

	public long getNumTrades() {
		return numTrades;
	}

	public void setNumTrades(long numTrades) {
		this.numTrades = numTrades;
	}

	public long getTotalVolumeTrade() {
		return totalVolumeTrade;
	}

	public void setTotalVolumeTrade(long totalVolumeTrade) {
		this.totalVolumeTrade = totalVolumeTrade;
	}

	public long getTotalValueTrade() {
		return totalValueTrade;
	}

	public void setTotalValueTrade(long totalValueTrade) {
		this.totalValueTrade = totalValueTrade;
	}

	public int getNoMDEntries() {
		return NoMDEntries;
	}

	public void setNoMDEntries(int noMDEntries) {
		NoMDEntries = noMDEntries;
	}
	
	protected void readBaseSnapshot(ByteBuf in) {
		this.origTime = in.readLong();
		this.channelNo = in.readShort();
		this.MDStreamID = BinaryUtil.getStringData(in, 3).trim();
		this.securityId = BinaryUtil.getStringData(in, 8).trim();
		this.securityIDSource = BinaryUtil.getStringData(in, 4).trim();
		this.tradingPhaseCode = BinaryUtil.getStringData(in, 8).trim();
		this.prevClosePx = new BigDecimal(in.readLong()).divide(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP);
		this.numTrades = in.readLong();
		this.totalVolumeTrade = in.readLong()/100;
		this.totalValueTrade = in.readLong()/100;
		this.NoMDEntries = in.readInt();
	}
	
	

	

}
