package com.ai.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static Logger defaultLogger;
	private static Log logInstance;
	public Log(){
		PropertyConfigurator.configure("config/log4j.properties");
	}
	
	public static Log getLog(String clazz){		
		logInstance=new Log();
		defaultLogger=Logger.getLogger(clazz);
		return logInstance;
	}
	
	public void debug(Object obj){
		defaultLogger.debug(obj);
	}
	
	public void info(Object obj){
		defaultLogger.info(obj);
	}
	
	public void warn(Object obj){
		defaultLogger.warn(obj);
	}
	
	public void error(Object obj){
		defaultLogger.error(obj);
	}
}
