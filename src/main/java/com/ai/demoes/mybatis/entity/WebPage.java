package com.ai.demoes.mybatis.entity;

import java.math.BigInteger;
import java.util.Date;

public class WebPage {
	private String url;
	private Date fetchTime;
	private String parentURL;
	private Date fetchingTime;
	private BigInteger pageIdentity;
	private String title;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getFetchTime() {
		return fetchTime;
	}
	public void setFetchTime(Date fetchTime) {
		this.fetchTime = fetchTime;
	}
	public String getParentURL() {
		return parentURL;
	}
	public void setParentURL(String parentURL) {
		this.parentURL = parentURL;
	}
	public Date getFetchingTime() {
		return fetchingTime;
	}
	public void setFetchingTime(Date fetchingTime) {
		this.fetchingTime = fetchingTime;
	}
	public BigInteger getPageIdentity() {
		return pageIdentity;
	}
	public void setPageIdentity(BigInteger pageIdentity) {
		this.pageIdentity = pageIdentity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	
	@Override 
	public String toString(){
		return new StringBuilder().append("PageIdentity:")
				.append(pageIdentity).append("\n")
				.append("URL:").append(url).append("\n")
				.append("ParentURL:").append(parentURL).append("\n")
				.append("Title:").append(title).toString();
	}
}
