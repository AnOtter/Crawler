package com.ai.crawler.entity;

/**
 * @author OTTER
 * @description 网页标题匹配正则式实体类
 */
public class TitlePattern {
	private String authrity;
	private String matchPattern;
		
	public String getAuthrity() {
		return authrity;
	}
	public void setAuthrity(String authrity) {
		this.authrity = authrity;
	}
	public String getMatchPattern() {
		return matchPattern;
	}
	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}	
}

