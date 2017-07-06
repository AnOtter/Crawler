package com.ai.crawl;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.ai.util.FileOperator.*;
import static com.ai.util.DateTime.*;

public class WebPage implements Comparable<WebPage>, Serializable {
	private static final long serialVersionUID = -4054304601865659456L;
	private URL url;
	private String title;
	private Date lastFetchTime;
	private URL parentPage;
	private String articleContent;
	private String localFilePath;
	private List<String> outerLinks;
	private String articleMatchPattern;
	
	private Document document;
	
	
	public WebPage() {
		outerLinks=new LinkedList<>();
		title="";
		articleContent="";
		articleMatchPattern="id=Cnt-Main-Article-QQ";	
		document=null;
	}
	
	public List<String> getOuterLinks() {
		if(outerLinks.size()==0)
			parseOuterLinks();
		return outerLinks;
	}

	public String getArticleContent() {
		if(articleContent.equals(""))
			parseArticle();
		return articleContent;
	}
	
	public void setArticleMatchPattern(String pattern) {
		this.articleMatchPattern=pattern;		
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getTitle() {
		if(title.equals(""))
			parserTitle();
		return title;
	}

	public Date getLastFetchTime() {
		return lastFetchTime;
	}

	public URL getParentPage() {
		return parentPage;
	}

	public void setParentPage(URL parentPage) {
		this.parentPage = parentPage;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public boolean equals(Object obj) {
		if (obj.getClass().equals(WebPage.class)) {
			return ((WebPage) obj).getUrl().equals(this.getUrl());
		}
		return false;
	}

	@Override
	public int compareTo(WebPage arg0) {
		Date fetchTime = arg0.getLastFetchTime();

		if (fetchTime == null) {
			if (lastFetchTime == null)
				return 0;
			else
				return -1;
		} else {
			if (lastFetchTime == null)
				return 1;
			else
				return fetchTime.compareTo(lastFetchTime);
		}
	}
	
	public boolean save() {
		if(!localFilePath.equals("") && !articleContent.equals(""))
		{
			String content="<html><head fetchTime=\""+now()+"\">"+getTitle()+"</head><body>"+getArticleContent()+"</body></html>";
			writeContent(localFilePath, content,false,false);
			return true;
		}
		return false;		
	}
	
	public boolean fetch() throws Exception {
		try {
			lastFetchTime=new Date();
			document=Jsoup.parse(url, 3000);
			return true;
		} catch (Exception e) {
			System.err.println("WebPage fetch Error:"+url.toString());
			return false;
		}
				
	}
	
	private void parserTitle(){
		if(document!=null)
		{
			Elements titles=document.select("h1");
			if(titles.size()==0)
				titles=document.select("title");
			if(titles.size()>=0)
				title=titles.get(0).text();				
		}
	}
	
	private void parseOuterLinks(){
		if(document!=null)
		{
			Elements links=document.select("a[href~=(i?)http.+]");				
			for (Element link : links) {
				outerLinks.add(link.attr("href"));
			}			
		}		
	}
	
	private void parseArticle(){
		if(document!=null && !articleMatchPattern.equals(""))
		{
			Elements articles=document.select("div["+articleMatchPattern+"]");
			if(articles.size()>0)
			{
				articleContent=articles.get(0).outerHtml();
			}
		}	
	}
}
