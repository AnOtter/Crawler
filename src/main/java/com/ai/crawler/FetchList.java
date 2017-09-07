package com.ai.crawler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;
@Component
public class FetchList {
	
	@Autowired 
	WebPageService webPageService;
	
	@Value("${FetchList.FetchCount}")
	int fetchCount;	

	/**
	 * @return 获取下次 需要爬取的页面列表
	 * @说明 查询配置文件中设置个数为 <code>FetchList.FetchCount</code> 个未爬取的页面列表
	 */
	public List<WebPage> getNextFetchPages() {
		List<WebPage> fetchList = webPageService.getFetchList(fetchCount);
		webPageService.updateFetchingTime(fetchList);
		return fetchList;
	}

}
