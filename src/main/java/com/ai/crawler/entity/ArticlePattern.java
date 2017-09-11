package com.ai.crawler.entity;

/**
 * @author OTTER
 * @description 网页文章内容匹配正则表达式实体类
 * @
 */
public class ArticlePattern {
	private String authority;
	private String matchPattern;
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getMatchPattern() {
		return matchPattern;
	}
	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}	
}
