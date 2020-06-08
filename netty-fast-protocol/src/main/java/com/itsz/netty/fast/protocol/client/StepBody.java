package com.itsz.netty.fast.protocol.client;

public class StepBody {

	public Integer sum;			//校验和值
	public Integer NoFooterLen;	//除去尾部tag为10部分的值
	public Integer AllLen;		//全部长度
	public Integer bodyLen;		//数据域的长度
	public Integer bodyStrLen;	//计算sum的body的str长度
	
}
