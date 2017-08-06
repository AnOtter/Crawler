package com.ai.crawl2;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

import org.jsoup.Jsoup;


/**
 * @author OTTER
 * @类说明 网页获取器(使用观察者模式)
 * @WebPage 要获取的页面
 * @Observers 观察者列表
 * @主要方法 fetch() 
 */
public class PageFetcher {
	private WebPage webPage;
	private LinkedList<FetcherObserver> observers;

	public WebPage getWebPage() {
		return webPage;
	}

	public void setWebPage(WebPage webPage) {
		this.webPage = webPage;
	}
	
	public void addObserver(FetcherObserver fetcherObserver){
		observers.add(fetcherObserver);
	}
	
	public void removeObserver(FetcherObserver fetcherObserver) {
		observers.remove(fetcherObserver);
	}
	
	private void updateObservers(){
		for(FetcherObserver observer :observers){
			observer.pageFetched(this.webPage);
		}
	}
	
	public void fetch(){
		try{
			if(!webPage.getUrl().equals("")){
				webPage.setLastFetchTime(new Date());
				webPage.setDocument(Jsoup.parse(new URL(webPage.getUrl()), 3000));
				updateObservers();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
