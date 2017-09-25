package com.ai.crawler;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;

@Component
public class FetchList {
	private Date lastUpdateSeedTime = new Date();

	@Autowired
	WebPageService webPageService;

	@Value("${FetchList.FetchCount}")
	int fetchCount;

	/**
	 * @return 获取下次 需要爬取的页面列表
	 * @说明 查询配置文件中设置个数为 <code>FetchList.FetchCount</code> 个未爬取的页面列表
	 */
	public List<WebPage> getNextFetchPages() {
		Date now = new Date();
		long interval = now.getTime() - lastUpdateSeedTime.getTime();
		if (interval > 180 * 60 * 1000) // 离上次更新主网址时间超过3小时再更新一次
			updateSeedURLFetchingTime();
		List<WebPage> fetchList = webPageService.getFetchList(fetchCount);
		if (fetchList.size() > 0)
			webPageService.updateFetchingTime(fetchList);
		return fetchList;
	}

	@PostConstruct
	private void updateSeedURLFetchingTime() {
		webPageService.updatSeedURL();
		lastUpdateSeedTime = new Date();
	}

}
