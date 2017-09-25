package com.ai.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ai.crawler.mapper.WebPageMapper;
import com.ai.crawler.PageURL;
import com.ai.crawler.entity.WebPage;

@Service
@Scope("prototype")
public class WebPageService {
	@Autowired
	private WebPageMapper webPageMapper;

	private boolean exist(WebPage webPage) {
		return webPageMapper.existCount(webPage) > 0;
	}

	public List<WebPage> getFetchList(int count) {
		return webPageMapper.getFetchList(count);
	}

	// public Date getLastFetchTime() {
	// return webPageMapper.getLastFetchTime();
	// }

	public long getMaxPageIdentity() {
		return webPageMapper.getMaxPageIdentity();
	}

	public WebPage getPageByIdentity(long pageIdentity) {
		return webPageMapper.getPageByIdentity(pageIdentity);
	}

	public WebPage getPageByURL(String url) {
		return webPageMapper.getPageByURL(url);
	}

	public List<WebPage> getPagesByKeyWord(String keyWord, int count) {
		return webPageMapper.getPagesByKeyWord(keyWord, count);
	}

	public boolean insert(WebPage webPage) {
		return webPageMapper.insert(webPage);
	}

	public boolean save(WebPage webPage) {
		if (exist(webPage))
			return update(webPage);
		else
			return insert(webPage);
	}

	public boolean update(WebPage webPage) {
		if (!PageURL.isDirectory(webPage.getUrl()))
			return webPageMapper.update(webPage);
		return true;
	}

	/**
	 * @param webPageList
	 * @return
	 * @description 批量更新fetchingTime
	 */
	public boolean updateFetchingTime(List<WebPage> webPageList) {
		return webPageMapper.updateFetchingTime(webPageList);
	}

	public boolean updatSeedURL() {
		return webPageMapper.updatSeedURL();
	}
}
