package com.itsz.netty.fast.protocol;

import com.itsz.netty.fast.protocol.client.OpenFastUtil;
import com.itsz.netty.fast.protocol.client.StepUtil;
import com.itsz.netty.fast.protocol.snapshot.BidBrokerQueue;
import com.itsz.netty.fast.protocol.snapshot.BidPriceLevel;
import com.itsz.netty.fast.protocol.snapshot.OffBrokerQueue;
import com.itsz.netty.fast.protocol.snapshot.OfferPriceLevel;
import io.netty.buffer.ByteBuf;
import org.openfast.*;
import org.openfast.codec.FastDecoder;
import org.openfast.template.BasicTemplateRegistry;
import org.openfast.template.MessageTemplate;
import org.openfast.template.TemplateRegistry;
import org.openfast.template.loader.MessageTemplateLoader;
import org.openfast.template.loader.XMLMessageTemplateLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SnapshotPacket extends AbstractPacket {

	private int dataTimeStamp; // 时间戳

	private int dataStatus; // 1=重复数据（无需处理和展示，只要检查序号是否连续） 2=未获授权（删除）

	private String securityID; // 证券代码，5 位长度

	private int imageStatus; // 快照类型 1=全量 2=更新

	private int highPx; // 最高价

	private int lowPx; // 最低价

	private int lastPx; // 现价

	private int closePx; // 收盘价

	private int norminalPx; // 按盘价,-1 表示该数值 无效

	private List<BidPriceLevel> bidPriceLevel = new ArrayList<BidPriceLevel>(); // 买 10

	private List<OfferPriceLevel> offerPriceLevel = new ArrayList<OfferPriceLevel>(); // 卖 10

	private List<BidBrokerQueue> bidBrokerQueue = new ArrayList<BidBrokerQueue>(); //

	private List<OffBrokerQueue> offBrokerQueue = new ArrayList<OffBrokerQueue>(); //

	private int yield; // 债券收益

	private int shortSellSharesTraded; // 抛空数量

	private int shortSellTurnover; // 抛空金额

	private int totalVolumeTrade; // 成交数量

	private int totalValueTrade; // 成交金额

	private int tradingStatus; // 2 Trading Halt 3 Resume

	@Override
	public void execute(ByteBuf in, int len) {
		for (;;) {
			int pos = in.bytesBefore((byte) StepUtil.STEP_SOH);
			ByteBuf bufNew = in.readBytes(pos);
			in.readByte();
			len = len -pos -1;
			byte[] bytes = new byte[pos];
			bufNew.readBytes(bytes);
			String[] values = new String(bytes).split("=");
			String tag = values[0];
			if ("95".equals(tag)) {
				rawDataLen = Integer.parseInt(values[1]);
				break;
			}
		}
		in.readBytes(len - rawDataLen-1);
		rawData = new byte[rawDataLen];
		in.readBytes(rawData);
		in.readBytes(StepUtil.constTrailerSize+1);
		FastDecoder fastDecoder = getFastDecoder(2202,"template.xml");
		Message message = fastDecoder.readMessage();
		
		SequenceValue offerSequence = message.getSequence("OfferPriceLevel");
		if(offerSequence!=null){
			for (GroupValue groupValue : offerSequence.getValues()) {
				OfferPriceLevel offerPriceLevel = new OfferPriceLevel();
				ScalarValue OfferSize =  groupValue.getScalar("OfferSize");
				ScalarValue OfferPx =  groupValue.getScalar("OfferPx");
				ScalarValue OfferOrderNumbers =  groupValue.getScalar("OfferOrderNumbers");
				offerPriceLevel.setOfferOrderNumbers(OpenFastUtil.scalar2Integer(OfferOrderNumbers));
				offerPriceLevel.setOfferSize(OpenFastUtil.scalar2Integer(OfferSize));
				offerPriceLevel.setOfferPx(OpenFastUtil.scalar2Integer(OfferPx));
				this.offerPriceLevel.add(offerPriceLevel);
			}
		}
		SequenceValue bidSequence = message.getSequence("BidPriceLevel");
		if(bidSequence!=null){
			for (GroupValue groupValue : bidSequence.getValues()) {
				BidPriceLevel bidPriceLevel = new BidPriceLevel();
				ScalarValue bidSize =  groupValue.getScalar("BidSize");
				ScalarValue bidPx =  groupValue.getScalar("BidPx");
				ScalarValue bidOrderNumbers =  groupValue.getScalar("BidOrderNumbers");
				bidPriceLevel.setBidOrderNumbers(OpenFastUtil.scalar2Integer(bidOrderNumbers));
				bidPriceLevel.setBidSize(OpenFastUtil.scalar2Integer(bidSize));
				bidPriceLevel.setBidPx(OpenFastUtil.scalar2Integer(bidPx));
				this.bidPriceLevel.add(bidPriceLevel);
			}
		}
		
		
		SequenceValue bidBrokerSequence = message.getSequence("BidBrokerQueue");
		if(bidBrokerSequence!=null){
			for (GroupValue groupValue : bidBrokerSequence.getValues()) {
				BidBrokerQueue bidBroker = new BidBrokerQueue();
				ScalarValue bidItem =  groupValue.getScalar("BidItem");
				ScalarValue bidType =  groupValue.getScalar("BidType");
				bidBroker.setBidItem(OpenFastUtil.scalar2Integer(bidItem));
				bidBroker.setBidType(bidType.toString());
				this.bidBrokerQueue.add(bidBroker);
			}
		}
		
		
		SequenceValue offBrokerSequence = message.getSequence("OffBrokerQueue");
		if(offBrokerSequence!=null){
			for (GroupValue groupValue : offBrokerSequence.getValues()) {
				OffBrokerQueue offBroker = new OffBrokerQueue();
				ScalarValue OffItem =  groupValue.getScalar("OffItem");
				ScalarValue offType =  groupValue.getScalar("OffType");
				offBroker.setOffItem(OpenFastUtil.scalar2Integer(OffItem));
				offBroker.setOffType(offType.toString());
				this.offBrokerQueue.add(offBroker);
			}
		}
		
		
		this.dataTimeStamp =OpenFastUtil.scalar2Integer(message.getScalar("DataTimestamp"));
		this.dataStatus =OpenFastUtil.scalar2Integer(message.getScalar("DataStatus"));
		this.securityID = message.getString("SecurityID");
		this.imageStatus = OpenFastUtil.scalar2Integer(message.getScalar("ImageStatus"));
		this.highPx = OpenFastUtil.scalar2Integer(message.getScalar("HighPx"));
		this.lowPx = OpenFastUtil.scalar2Integer(message.getScalar("HighPx"));
		this.lastPx = OpenFastUtil.scalar2Integer(message.getScalar("LastPx"));
		this.closePx= OpenFastUtil.scalar2Integer(message.getScalar("ClosePx"));
		this.norminalPx =  OpenFastUtil.scalar2Integer(message.getScalar("NorminalPx"));
		this.shortSellSharesTraded = OpenFastUtil.scalar2Integer(message.getScalar("ShortSellSharesTraded"));
		this.shortSellTurnover = OpenFastUtil.scalar2Integer(message.getScalar("ShortSellTurnover"));
		this.totalVolumeTrade = OpenFastUtil.scalar2Integer(message.getScalar("TotalVolumeTrade"));
		this.totalValueTrade = OpenFastUtil.scalar2Integer(message.getScalar("TotalValueTrade"));
		this.tradingStatus = OpenFastUtil.scalar2Integer(message.getScalar("TradingStatus"));
	}

	private FastDecoder getFastDecoder(int templateId,String fileName) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(rawData);
		InputStream fileInputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(fileName);
		MessageTemplateLoader loader = new XMLMessageTemplateLoader();
		MessageTemplate[] templates = loader.load(fileInputStream);
		MessageTemplate messageTemplate = templates[0];
		TemplateRegistry reigistry = loader.getTemplateRegistry();
		reigistry.register(templateId, messageTemplate);
		Context context = new Context();
		context.setTemplateRegistry(reigistry);
		FastDecoder fastDecoder = new FastDecoder(context, inputStream);
		return fastDecoder;
	}

	public int getDataTimeStamp() {
		return dataTimeStamp;
	}

	public void setDataTimeStamp(int dataTimeStamp) {
		this.dataTimeStamp = dataTimeStamp;
	}

	public int getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public int getImageStatus() {
		return imageStatus;
	}

	public void setImageStatus(int imageStatus) {
		this.imageStatus = imageStatus;
	}

	public int getHighPx() {
		return highPx;
	}

	public void setHighPx(int highPx) {
		this.highPx = highPx;
	}

	public int getLowPx() {
		return lowPx;
	}

	public void setLowPx(int lowPx) {
		this.lowPx = lowPx;
	}

	public int getLastPx() {
		return lastPx;
	}

	public void setLastPx(int lastPx) {
		this.lastPx = lastPx;
	}

	public int getClosePx() {
		return closePx;
	}

	public void setClosePx(int closePx) {
		this.closePx = closePx;
	}

	public int getNorminalPx() {
		return norminalPx;
	}

	public void setNorminalPx(int norminalPx) {
		this.norminalPx = norminalPx;
	}

	public List<BidPriceLevel> getBidPriceLevel() {
		return bidPriceLevel;
	}

	public void setBidPriceLevel(List<BidPriceLevel> bidPriceLevel) {
		this.bidPriceLevel = bidPriceLevel;
	}

	public List<OfferPriceLevel> getOfferPriceLevel() {
		return offerPriceLevel;
	}

	public void setOfferPriceLevel(List<OfferPriceLevel> offerPriceLevel) {
		this.offerPriceLevel = offerPriceLevel;
	}

	public List<BidBrokerQueue> getBidBrokerQueue() {
		return bidBrokerQueue;
	}

	public void setBidBrokerQueue(List<BidBrokerQueue> bidBrokerQueue) {
		this.bidBrokerQueue = bidBrokerQueue;
	}

	public List<OffBrokerQueue> getOffBrokerQueue() {
		return offBrokerQueue;
	}

	public void setOffBrokerQueue(List<OffBrokerQueue> offBrokerQueue) {
		this.offBrokerQueue = offBrokerQueue;
	}

	public int getYield() {
		return yield;
	}

	public void setYield(int yield) {
		this.yield = yield;
	}

	public int getShortSellSharesTraded() {
		return shortSellSharesTraded;
	}

	public void setShortSellSharesTraded(int shortSellSharesTraded) {
		this.shortSellSharesTraded = shortSellSharesTraded;
	}

	public int getShortSellTurnover() {
		return shortSellTurnover;
	}

	public void setShortSellTurnover(int shortSellTurnover) {
		this.shortSellTurnover = shortSellTurnover;
	}

	public int getTotalVolumeTrade() {
		return totalVolumeTrade;
	}

	public void setTotalVolumeTrade(int totalVolumeTrade) {
		this.totalVolumeTrade = totalVolumeTrade;
	}

	

	public int getTotalValueTrade() {
		return totalValueTrade;
	}

	public void setTotalValueTrade(int totalValueTrade) {
		this.totalValueTrade = totalValueTrade;
	}

	public int getTradingStatus() {
		return tradingStatus;
	}

	public void setTradingStatus(int tradingStatus) {
		this.tradingStatus = tradingStatus;
	}
	
	

	@Override
	public String toString() {
		return "SnapshotPacket [dataTimeStamp=" + dataTimeStamp + ", dataStatus=" + dataStatus + ", securityID="
				+ securityID + ", imageStatus=" + imageStatus + ", highPx=" + highPx + ", lowPx=" + lowPx + ", lastPx="
				+ lastPx + ", closePx=" + closePx + ", norminalPx=" + norminalPx + ", bidPriceLevel=" + bidPriceLevel
				+ ", offerPriceLevel=" + offerPriceLevel + ", bidBrokerQueue=" + bidBrokerQueue + ", offBrokerQueue="
				+ offBrokerQueue + ", yield=" + yield + ", shortSellSharesTraded=" + shortSellSharesTraded
				+ ", shortSellTurnover=" + shortSellTurnover + ", totalVolumeTrade=" + totalVolumeTrade
				+ ", totalValueTrade=" + totalValueTrade + ", tradingStatus=" + tradingStatus + "]";
	}

	public static void main(String[] args) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File("D:/temp.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("template.xml");
		MessageTemplateLoader loader = new XMLMessageTemplateLoader();
		MessageTemplate[] templates = loader.load(inputStream);
		BasicTemplateRegistry reigistry = new BasicTemplateRegistry();
		reigistry.register(2202, templates[0]);
		Context context = new Context();
		context.setTemplateRegistry(reigistry);
		FastDecoder fastDecoder = new FastDecoder(context, fileInputStream);
		Message message = fastDecoder.readMessage();
		Iterator<?> iterator = message.iterator();
		while (iterator.hasNext()) {
			Object group = iterator.next();
			System.out.println(group);
		}

	}

}
