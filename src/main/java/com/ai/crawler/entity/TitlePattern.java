package com.ai.crawler.entity;

/**
 * @author OTTER
 * @description 网页标题匹配正则式实体类
 */
public class TitlePattern {
	private String authority;
	private String matchPattern;
		
	public String getAuthority() {
		return authority;
	}
	public String getMatchPattern() {
		return matchPattern;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}	
}

