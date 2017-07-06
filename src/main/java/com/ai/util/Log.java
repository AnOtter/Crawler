package com.ai.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static Logger defaultLogger;
	private Log(){
		PropertyConfigurator.configure("config/log4j.properties");
		defaultLogger=Logger.getLogger("Crawler");
	}
	
	public static void logDebug(Object obj){
		defaultLogger.debug(obj);
	}
	
	public static void logInfo(Object obj){
		defaultLogger.info(obj);
	}
	
	public static void logWarn(Object obj){
		defaultLogger.warn(obj);
	}
	
	public static void logError(Object obj){
		defaultLogger.error(obj);
	}
}
