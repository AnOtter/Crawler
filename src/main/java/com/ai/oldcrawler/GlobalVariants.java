package com.ai.oldcrawler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author OTTER
 * @class 全局静态变量类
 *
 */
@Configuration
@ConfigurationProperties
public class GlobalVariants {
	@Value("${localSaveDirectory}")
	private String localSaveDirectory="";
	
	@Value("${maxThreadCount}")
	private int maxThreadCount;

	public String getLocalSaveDirectory() {
		return localSaveDirectory;
	}

	public void setLocalSaveDirectory(String localSaveDirectory) {
		this.localSaveDirectory = localSaveDirectory;
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}	
}
