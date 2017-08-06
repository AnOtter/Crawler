package com.ai.crawl2;

import java.util.Date;

import org.jsoup.nodes.Document;



/**
 * @author OTTER
 * @类说明 网页页面实体类  
 * @URL 页面URL
 * @ParentURL 父页面地址
 * @LastFetchTime 最后一次爬取时间
 * @Document 爬取之后的页面内容 
 */
public class WebPage {
	private String url;
	private Date lastFetchTime;
	private String parentURL;
	private Document document;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getLastFetchTime() {
		return lastFetchTime;
	}
	public void setLastFetchTime(Date lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}
	public String getParentURL() {
		return parentURL;
	}
	public void setParentURL(String parentURL) {
		this.parentURL = parentURL;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}	
	
	public WebPage(String url){
		this.url =url;
		this.lastFetchTime=null;
		this.parentURL="";
		this.document=null;				
	}
	
	public WebPage(String url,String parentURL){
		this.url =url;
		this.lastFetchTime=null;
		this.parentURL=parentURL;
		this.document=null;				
	}
}
