package com.ai.crawl2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author OTTER
 * @类说明 页面爬取之后将页面信息保存到数据库
 */
@Component
public class DBInfoObserver implements FetcherObserver {
	@Autowired
	WebPageService webPageService;

	@Override
	public void pageFetched(WebPage webPage) {
		webPageService.savePage(webPage);
	}
}
