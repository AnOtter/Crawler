package com.ai.util;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static Logger defaultLogger;

	private static void initializeLog() {
		try {			
			if (defaultLogger == null) {
				String currentPath = Log.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String currentDirectory = new File(currentPath).getParent();
				PropertyConfigurator.configure(currentDirectory + "/config/log4j.properties");
				defaultLogger = Logger.getLogger("Crawler");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void logDebug(Object obj) {
		initializeLog();
		defaultLogger.debug(obj);
	}

	public static void logInfo(Object obj) {
		initializeLog();
		defaultLogger.info(obj);
	}

	public static void logWarn(Object obj) {
		initializeLog();
		defaultLogger.warn(obj);
	}

	public static void logError(Object obj) {
		initializeLog();
		defaultLogger.error(obj);
	}
}
