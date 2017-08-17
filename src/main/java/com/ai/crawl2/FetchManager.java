package com.ai.crawl2;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class FetchManager implements ApplicationContextAware {

	@Autowired
	PageFetcher pageFetcher;
	@Autowired
	FetchList fetchList;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}

	@PostConstruct
	public void run() {
		try {
			List<WebPage> nextFetchList = fetchList.getNextFetchPage();
			while (nextFetchList.size() > 0) {
				for (WebPage webPage : nextFetchList) {
					fetchPage(webPage);
				}
				nextFetchList = fetchList.getNextFetchPage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fetchPage(WebPage webPage) {
		try {
			pageFetcher.fetch(webPage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
