package com.share.jack.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static String getNowTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}