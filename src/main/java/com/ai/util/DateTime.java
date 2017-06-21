package com.ai.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	public static final String datePatter="yyyy-MM-dd HH:mm:ss SSS";
	public static String now(){
		   return formatDate(new Date());
	   }
	   public static String formatDate(Date date){
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat(datePatter);
			String formatedDate=simpleDateFormat.format(date);
			return formatedDate;
	   }

	   public static Date parseDate(String formetedDate) throws ParseException{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(datePatter);
		Date formatedDate=simpleDateFormat.parse(formetedDate);
		return formatedDate;
	   }
}