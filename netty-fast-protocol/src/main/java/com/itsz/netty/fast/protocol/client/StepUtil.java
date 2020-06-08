package com.itsz.netty.fast.protocol.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 通用的STEP协议
 * @author kh.luo
 *	1处理tag=val<SOH>
 *	2 
 */
public class StepUtil {

	static final String 	STEP_VERSION = "STEP.1.0.0";
	public static final char 		STEP_SOH = 0X01;
	static final String		STEP_Sender = "VSS";
	static final String		STEP_Recevier = "VDE";
	static final Integer 	STEP_HeartBeatTime = 5;
	
	static String constHeader = "8="+STEP_VERSION+STEP_SOH+"9=";
	static final String stepVersionHeader = "8="+STEP_VERSION+STEP_SOH;
	static final int stepVersionHeaderSize = stepVersionHeader.length();
	static final Integer 	constHeaderSize = constHeader.length();
	static final int		minHeaderSize = constHeaderSize+3;
	public static final int constTrailerSize =("10=000"+STEP_SOH).length();
	
	
	public static int getMinHeaderSize(){
		return minHeaderSize;
	}
	
	//除了 10 域本身，对所有其他域的每个字节累加后取256的余数。余数不足 3 位的，前补 0。
	//8=STEP.1.0.0<SOH>9=99<SOH>35=UA3115<SOH>49=VDE<SOH>56=VDR<SOH>34=0<SOH>52=20101019-09:07:11
	
	/**
	 * 获取标准头
	 * @param msgSeqNum
	 * @param msgType
	 * @param bodyLen
	 * @return
	 */
	public static String getStandardHeader(Integer msgSeqNum, String msgType, StepBody steBody){
		String msgHeader = new String();
		//取最新时间
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
		String strDt = dtf.format(LocalDateTime.now());
		//--
		String headerOther = "35="+msgType+STEP_SOH+"49="+STEP_Sender+STEP_SOH+"56="+STEP_Recevier
				+STEP_SOH+"34=0"+STEP_SOH+"52="+strDt+STEP_SOH;
		//更新body长度
		steBody.bodyLen += headerOther.length();//1+headerOther.length()
		//补固定头部
		msgHeader = "8="+STEP_VERSION+STEP_SOH+"9="+steBody.bodyLen+STEP_SOH
				+ headerOther;
		
		return msgHeader;
	}
	
	/**
	 * 获取标准尾部
	 * @param buffs
	 * @return
	 */
	public static String getStandardFooter(byte buffs[]){
		Integer checkSum = 0;
		for(int i=0; i<buffs.length; i++){
			checkSum+=buffs[i];
		}
		checkSum = checkSum % 256;
		
		//添加尾部
		String msgFooter = buffs + "10="+ String.format("%03d",  checkSum);
		return msgFooter;
	}
	
	/**
	 * 组装消息
	 * @param msgSeqNum -消息序号
	 * @param msgType	-消息类型
	 * @param msgBody	-消息数据域数据
	 * @return
	 */
	public static String getMessage(Integer msgSeqNum, String msgType, String msgBody){
		StepBody stepBody =  new StepBody(); stepBody.bodyLen = msgBody.length();
		String header = StepUtil.getStandardHeader( msgSeqNum, msgType, stepBody) + msgBody;
		//String bodyOther = header.substring(constHeaderSize+stepBody.bodyLen.toString().length()+1, header.length()) ;
		Integer checkSum = 0;
		byte buffs[] = header.getBytes();
		for(int i=0; i<buffs.length; i++){
			checkSum+=buffs[i];
		}
		checkSum = checkSum % 256;
		
		String msg = header + "10="+ String.format("%03d",  checkSum)+STEP_SOH;
		return msg;
	}
	
	//登录指令
	public static String Logon(){
		String logonMsg = "98=0"+STEP_SOH+"108="+STEP_HeartBeatTime+STEP_SOH;
		
		return StepUtil.getMessage(0, "A", logonMsg );
	}
	
	//登出指令
	public static String Logout(){
		String logoutMsg = "58="+"Data rebuild request responded."+STEP_SOH;
		return StepUtil.getMessage(0, "5", logoutMsg );
	}
	
	//心跳指令
	public static String HeartBeat(){
		String logoutMsg = "10072=-1"+STEP_SOH+"58="+"happy."+STEP_SOH;
		return StepUtil.getMessage(0, "UA1202", logoutMsg );
	}
	
	//登录指令
	public static String SZLogon(){
		String logonMsg = "SenderCompID="+STEP_Sender+"TargetCompID="+STEP_Recevier+"HeartBtInt="+STEP_HeartBeatTime+"Password=123456"+"DefaultApplVerID=1.02";
		String header = "MsgType=1"+"BodyLength="+logonMsg.length()+logonMsg;
		Integer checkSum = 0;
		byte buffs[] = header.getBytes();
		for(int i=0; i<buffs.length; i++){
			checkSum+=buffs[i];
		}
		checkSum = checkSum % 256;
		String msg = header+"CheckSum="+ String.format("%03d",  checkSum);
		return msg;
	}
	
	
	/**
	 * 判断-是否可以拆包
	 * @param buffHeader
	 * @param bufLen
	 * @param steBody  --返回
	 * @return
	 */
	public static boolean isFullPackage(String buffHeader,int bufLen, StepBody stepBody ){
		boolean isFull = false;
		Integer AllLen, sum, bodyLen;
		int iPos = buffHeader.indexOf(STEP_SOH, constHeaderSize );
		if( iPos>0 ){
			String strBodyLen = buffHeader.substring( constHeaderSize, iPos);
			bodyLen = Integer.parseInt(strBodyLen);
			AllLen = constHeaderSize + strBodyLen.length() + 1 + bodyLen + 6 ;
			if( AllLen >= bufLen ){
				isFull = true;
				String sumStr = buffHeader.substring( AllLen-3, AllLen) ;
				sum = Integer.parseInt( sumStr );
				
				stepBody.AllLen = AllLen;
				stepBody.NoFooterLen = AllLen - 6;
				stepBody.bodyLen = bodyLen;
				stepBody.bodyStrLen = strBodyLen.length();
				stepBody.sum = sum;
			}
		}
		return isFull;
	}
	
	//public static boolean is
	
	/**
	 * 检测校验和是否正确
	 * 除了 10 域本身，对所有其他域的每个字节累加后取256的余数。余数不足 3 位的，前补 0。
	 * @param buff
	 * @param bodyLen
	 * @return
	 */
	public static boolean checkSum(String buff, StepBody stepBody ){
		boolean isOK = false;
		int bodySpLen, sum; bodySpLen = stepBody.NoFooterLen; sum = stepBody.sum;
		String strSum = buff.substring(constHeaderSize + stepBody.bodyStrLen +1, bodySpLen);
		int checkSum = 0;
		byte buffs[] = strSum.getBytes();
		for(int i=0; i<buffs.length; i++){
			checkSum+=buffs[i];
		}
		checkSum = checkSum % 256;
		if( sum ==  checkSum ){
			return true;
		}
		return isOK;
	}
	
	
}
