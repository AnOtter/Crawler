package com.ai.crawl2;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author OTTER
 * @类说明 页面爬取之后获取关联页面地址列表
 */
@Component
public class SubPageObserver implements FetcherObserver {
	@Autowired
	WebPageService webPageService;

	@Override
	public void pageFetched(WebPage webPage) {
		List<String> subPageURLs=getSubPageURLs(webPage);
		saveSubPageURLs(subPageURLs,webPage.getUrl());
	}
	
	private List<String> getSubPageURLs(WebPage fetchedPage){
		List<String> subPageURLs=new LinkedList<>();
		Document document =fetchedPage.getDocument();
		if(document!=null)
		{
			Elements links=document.select("a[href~=(i?)http.+]");				
			for (Element link : links) {
				subPageURLs.add(link.attr("href"));
			}			
		}	
		return subPageURLs;
	}
	
	private void saveSubPageURLs(List<String> subPageURLs,String pageURL){
		for(String subPageURL:subPageURLs){			
			WebPage subPage=new WebPage(subPageURL, pageURL);
			saveSubPage(subPage);
		}
	}
	
	private void saveSubPage(WebPage subPage){
		webPageService.savePage(subPage);
	}
}
