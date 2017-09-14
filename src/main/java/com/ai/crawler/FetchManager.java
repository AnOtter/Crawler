package com.ai.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;

/**
 * @author OTTER
 * @since 2017.7.7
 * @description 程序主入口类
 */
@Component
public class FetchManager implements ApplicationContextAware {

	@Autowired
	PageFetcher pageFetcher;

	@Autowired
	FetchList fetchList;

	@Value("${Crawler.MaxThreadCount}")
	int maxThreadCount;

	private ApplicationContext appContext;

	/**
	 * @param nextFetchList
	 *            下一批爬取页面列表
	 * @param executorService
	 *            爬取页面线程池
	 * @param futures
	 *            线程池结果列表
	 * 
	 * @description 向线程池加入新的爬取任务
	 */
	private void addFetchExecutor(List<WebPage> nextFetchList, ExecutorService executorService,
			List<Future<?>> futures) {
		if (futures.size() < maxThreadCount) {
			WebPage nextFetchPage = nextFetchList.remove(0);
			Future<?> future = getFetchFuture(executorService, nextFetchPage);
			futures.add(future);
		}
	}

	/**
	 * @param executorService
	 *            爬取页面线程池
	 * @param nextFetchPage
	 *            下一批爬取页面列表
	 * @return 新的爬取任务
	 * @description 获取新的页面爬取任务
	 */
	private Future<?> getFetchFuture(ExecutorService executorService, WebPage nextFetchPage) {
		pageFetcher = appContext.getBean(PageFetcher.class);
		pageFetcher.setFetchingPage(nextFetchPage);
		return executorService.submit(pageFetcher);
	}

	/**
	 * @param futures
	 *            线程池结果列表
	 * @description 移除已完成的线程
	 */
	private void removeDoneExecutors(List<Future<?>> futures) {
		for (int i = 0; i < futures.size(); i++) {
			if (futures.get(i).isDone())
				futures.remove(i);
		}
	}

	/**
	 * 程序主入口
	 * 
	 * @用处 循环获取数据库中的未爬取页面传递给pageFetcher执行页面爬取任务
	 */
	@PostConstruct
	public void run() {
		try {
			List<WebPage> nextFetchList = fetchList.getNextFetchPages();
			ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount);
			List<Future<?>> futures = new ArrayList<>();
			while (!nextFetchList.isEmpty()) {
				removeDoneExecutors(futures);
				addFetchExecutor(nextFetchList, executorService, futures);
				if (nextFetchList.isEmpty())
					nextFetchList = fetchList.getNextFetchPages();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}
}
