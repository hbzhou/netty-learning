package com.itsz.netty.binary;

public enum EnumPacketType {
    LOGONRESPONSE(1,"登陆返回消息"),
	HEARTBEAT(3, "盘后心跳"), 
	HEARTBEAT_SNAPSHOT(390095, "盘中心跳"), 
	SNAPSHOT(300111, "盘中竞价行情快照"), 
	POST_SNAPSHOT(300611, "盘后行情"), 
	INDEX_SNAPSHOT(309011,"指数行情快照"),
	;
	private int type;

	private String desc;

	private EnumPacketType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
