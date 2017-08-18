package com.ai.crawl2;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author OTTER
 * @类说明 网页获取器(使用观察者模式)
 * @WebPage 要获取的页面
 * @Observers 观察者列表
 */
@Component
@Scope("prototype")
public class PageFetcher implements Runnable {
	private LinkedList<FetcherObserver> observers = new LinkedList<>();

	private WebPage fetchingPage;

	// @Autowired
	// LocalFileObserver localFileObserver;

	@Autowired
	SubPageObserver subPageObserver;

	@Autowired
	DBInfoObserver dbInfoObserver;

	public WebPage getFetchingPage() {
		return fetchingPage;
	}

	public void setFetchingPage(WebPage fetchingPage) {
		this.fetchingPage = fetchingPage;
	}

	@PostConstruct
	private void addObservers() {
		// observers.add(localFileObserver);
		observers.add(dbInfoObserver);
		observers.add(subPageObserver);
	}

	private void updateObservers(WebPage webPage) {
		for (FetcherObserver observer : observers) {
			observer.pageFetched(webPage);
		}
	}

	/**
	 * @param webPage 需要爬取的网页
	 * @description 使用jsoup爬取网页内容，并通知观察者处理已爬取的内容
	 */
	public void fetch(WebPage webPage) {
		try {
			String fetchURL = webPage.getUrl();
			if (!fetchURL.equals("")) {
				System.out.println("fetching "+fetchURL);
				webPage.setLastFetchTime(new Date());
				URL url = new URL(fetchURL);
				Document document = fetchPage(url);
				webPage.setDocument(document);
				updateObservers(webPage);
			}
		} catch (Exception e) {
			System.err.println("fetching ERROR:" + webPage);
		}
	}

	/**
	 * @param url 爬取页面的url
	 * @return 爬取到的文档
	 * @description 爬取网页内容
	 */
	private Document fetchPage(URL url) {
		Document document = null;
		try {
			if (!url.getAuthority().contains("v.qq.com"))
				document = Jsoup.parse(url, 3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	@Override
	public void run() {
		if (fetchingPage != null)
			fetch(fetchingPage);
	}

}
