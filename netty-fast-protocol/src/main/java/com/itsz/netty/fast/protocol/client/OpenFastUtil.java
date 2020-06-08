package com.itsz.netty.fast.protocol.client;

import org.openfast.ScalarValue;

public class OpenFastUtil {
	
	public static int scalar2Integer(ScalarValue value){
		if(value==null){
			return 0;
		}
		return value.toInt();
	}

}
