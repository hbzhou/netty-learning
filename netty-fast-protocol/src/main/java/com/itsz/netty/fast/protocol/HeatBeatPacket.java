package com.itsz.netty.fast.protocol;


import com.itsz.netty.fast.protocol.client.StepUtil;
import io.netty.buffer.ByteBuf;
import org.openfast.template.loader.XMLMessageTemplateLoader;

public class HeatBeatPacket extends AbstractPacket {
	

	@Override
	public void execute(ByteBuf in, int len) {
		byte[] dst = new byte[len];
		in.readBytes(dst);
	    content = new String(dst);
	    in.readBytes(StepUtil.constTrailerSize);
	   
	}
	
	
	
	public static void main(String[] args) {
		XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader(); 
	}
	
	
}
