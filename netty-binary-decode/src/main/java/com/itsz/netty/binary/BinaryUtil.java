package com.itsz.netty.binary;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class BinaryUtil {
	
	public static final String		SENDER = "VSS";
	public static final int  LEN_SENDER = 20;
	public static final String		RECEVIER = "VDE";
	public static final int LEN_RECEVIER = 20;
	public static final String  DEFAULTAPPLVERID = "1.00";
	public static final int LEN_DEFAULTAPPLVERID=32;
	public static final String  PASSWORD = "123456";
	public static final int LEN_PASSWORD = 16;
	public static final int MSGTYPE = 1;
	public static final int HEADLEN = 8;
	public static final int HEART_BEAT_MSG_TYPE = 3;
	

	public static byte[] append(String str,int len) {
		StringBuilder sb = new StringBuilder(str);
		while(sb.length()<len){
			sb.append(" ");
		}
		return sb.toString().getBytes();
	}
	
	public static String getStringData(ByteBuf byteBuf, int len){
		byte[] data = new byte[len];
		byteBuf.readBytes(data);
		try {
			return new String(data,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
