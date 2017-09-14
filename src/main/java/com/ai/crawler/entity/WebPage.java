package com.ai.crawler.entity;

import java.math.BigInteger;
import java.util.Date;

import org.jsoup.nodes.Document;

/**
 * @author OTTER
 * @description 网页实体类
 * @url 网页的url地址
 * @parent URL 父页面url地址
 * @fetchTime 爬取完成时间
 * @fetchingTime 从数据库加载到任务时间
 * @title 页面标题
 * @document 爬取到的页面内容（JSoup）
 * @content 匹配到的文章内容
 */
public class WebPage {
	private String url;
	private Date fetchTime;
	private String parentURL;
	private Date fetchingTime;
	private BigInteger pageIdentity;
	private String title;
	private Document document;
	private String content;

	public String getContent() {
		if (content == null)
			return "";
		else
			return content;
	}

	public Document getDocument() {
		return document;
	}

	public Date getFetchingTime() {
		return fetchingTime;
	}

	public Date getFetchTime() {
		return fetchTime;
	}

	public BigInteger getPageIdentity() {
		return pageIdentity;
	}

	public String getParentURL() {
		return parentURL;
	}

	public String getTitle() {
		if (title == null)
			return "";
		else
			return title;
	}

	public String getUrl() {
		return url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setFetchingTime(Date fetchingTime) {
		this.fetchingTime = fetchingTime;
	}

	public void setFetchTime(Date fetchTime) {
		this.fetchTime = fetchTime;
	}

	public void setPageIdentity(BigInteger pageIdentity) {
		this.pageIdentity = pageIdentity;
	}

	public void setParentURL(String parentURL) {
		this.parentURL = parentURL;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("PageIdentity:").append(pageIdentity).append("\n").append("URL:").append(url)
				.append("\n").append("ParentURL:").append(parentURL).append("\n").append("Title:").append(title)
				.toString();
	}
}
