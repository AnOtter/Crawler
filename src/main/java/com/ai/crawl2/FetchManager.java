package com.ai.crawl2;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author OTTER
 * @类说明 程序主入口类
 */
@Component
public class FetchManager implements ApplicationContextAware {

	@Autowired
	PageFetcher pageFetcher;
	@Autowired
	FetchList fetchList;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}

	/**
	 * 程序主入口
	 * @用处 循环获取数据库中的未爬取页面传递给pageFetcher执行页面爬取任务
	 * @TODO 使用多线程加快执行速度
	 */
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

	/**
	 * @param webPage 需要爬取的页面信息
	 * @用处 爬取网页
	 */
	private void fetchPage(WebPage webPage) {
		try {
			pageFetcher.fetch(webPage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
