package com.ai.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static Logger defaultLogger;
	private static void initializeLog(){
		if(defaultLogger==null){
			PropertyConfigurator.configure("config/log4j.properties");
			defaultLogger=Logger.getLogger("Crawler");
		}
	}
	
	public static void logDebug(Object obj){
		initializeLog();
		defaultLogger.debug(obj);
	}
	
	public static void logInfo(Object obj){
		initializeLog();
		defaultLogger.info(obj);
	}
	
	public static void logWarn(Object obj){
		initializeLog();
		defaultLogger.warn(obj);
	}
	
	public static void logError(Object obj){
		initializeLog();
		defaultLogger.error(obj);
	}
}
