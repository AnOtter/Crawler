package com.ai.crawl2;
import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Component;

@Component
public class Log {
	private Logger defaultLogger=null;
	@PostConstruct
	private void initializeLog() {
		try {
				String currentPath = Log.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String currentDirectory = new File(currentPath).getParent();
				PropertyConfigurator.configure(currentDirectory + "/config/log4j.properties");
				defaultLogger = Logger.getLogger("Crawler");
				logInfo("Log setting file:" + currentDirectory + "/config/log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logDebug(Object obj) {
		defaultLogger.debug(obj);
	}

	public void logInfo(Object obj) {
		defaultLogger.info(obj);
	}

	public void logWarn(Object obj) {
		defaultLogger.warn(obj);
	}

	public void logError(Object obj) {
		defaultLogger.error(obj);
	}
}
