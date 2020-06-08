package com.itsz.netty.fast.protocol.client;

public class HeartBeatMessage {
	
	private String heartbeat;

	public String getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(String heartbeat) {
		this.heartbeat = heartbeat;
	}

	@Override
	public String toString() {
		return "HeartBeatMessage [heartbeat=" + heartbeat + "]";
	}
	
	

}