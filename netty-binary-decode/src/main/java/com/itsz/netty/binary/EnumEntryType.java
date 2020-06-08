package com.itsz.netty.binary;

public enum EnumEntryType {
	
	
	BUY("0","买"),
	SELL("1","卖"),
	LASTPRICE("2","最新价"),
	OPEN("4","开盘价"),
	HIGH("7","最高价"),
	LOW("8","最低价"),
	RF1("x1","升跌一"),
	RF2("x2","升跌二"),
	PE1("x5","股票市盈率一"),
	PE2("x6","股票市盈率二"),
	UPLIMITPRICE("xe","涨停价"),
	UPDOWNPRICE("xf","跌停价"),
	
	//指数
	LAST_INDEX("3","当前指数"),
	PRECLOSE_INDEX("xa","昨日收盘指数"),
	OPEN_INDEX("xb","开盘指数"),
	HIGH_INDEX("xc","最高指数"),
	LOW_INDEX("xd","最低指数"),
	;
	
	
	private String type;
	
	private String desc;
	

	private EnumEntryType(String type, String desc) {
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
