package com.ai.crawler.obserers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;

/**
 * @author OTTER
 * @类说明 页面爬取之后将页面信息保存到数据库
 */
@Component
@Scope("prototype")
public class DBInfoObserver implements FetcherObserver {
	@Autowired
	WebPageService webPageService;

	/** 
	 *@说明 将爬取到的页面的标题和爬取时间更新到数据库
	 */
	@Override
	public void pageFetched(WebPage webPage) {
		webPageService.save(webPage);
	}
}
