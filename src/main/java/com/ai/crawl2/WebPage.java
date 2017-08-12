package com.ai.crawl2;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
	protected String titlePattern;
	protected String articlePattern;

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

	public String getTitlePattern() {
		return titlePattern;
	}

	public void setTitlePattern(String titlePattern) {
		this.titlePattern = titlePattern;
	}

	public String getArticlePattern() {
		return articlePattern;
	}

	public void setArticlePattern(String articlePattern) {
		this.articlePattern = articlePattern;
	}

	public WebPage(String url) {
		this.url = url;
		this.lastFetchTime = null;
		this.parentURL =null;
		this.document = null;
		this.titlePattern="";
		this.articlePattern="";
	}

	public WebPage(String url, String parentURL) {
		this.url = url;
		this.lastFetchTime = null;
		this.parentURL = parentURL;
		this.document = null;
		this.titlePattern="";
		this.articlePattern="";
	}
	
	public String parserTitle(){
		if(document!=null)
		{
			Elements titles=document.select("h1");
			if(titles.size()==0 && (!titlePattern.equals(""))){
				titles=document.select(titlePattern);
			}
			if(titles.size()>0)
				return titles.get(0).text();
			else 
				return "";
		}
		return "";
	}
}
