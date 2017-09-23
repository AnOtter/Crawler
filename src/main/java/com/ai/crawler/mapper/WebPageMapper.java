package com.ai.crawler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;

@Component
public interface WebPageMapper {
	int existCount(WebPage webPage);

	List<WebPage> getFetchList(int count);

	WebPage getPageByIdentity(long pageIdentity);

	WebPage getPageByURL(String url);

	List<WebPage> getPagesByKeyWord(@Param("keyword") String keyWord, @Param("count") int count);

	boolean insert(WebPage webPage);

	boolean update(WebPage webPage);

	boolean updateFetchingTime(List<WebPage> webPages);
	
	long getMaxPageIdentity();
	
//	Date getLastFetchTime();
}
