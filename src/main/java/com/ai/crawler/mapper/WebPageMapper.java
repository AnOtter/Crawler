package com.ai.crawler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;

@Component
public interface WebPageMapper {
	List<WebPage> getFetchList(int count);
	
	boolean updateFetchingTime(List<WebPage> webPages);
	
	int existCount(WebPage webPage);
	
	boolean insert(WebPage webPage);
	
	boolean update(WebPage webPage);
	
	WebPage getPageByURL(String url);
	
	WebPage getPageByIdentity(long pageIdentity);
	
	List<WebPage> getPagesByKeyWord(@Param("keyword") String keyWord,@Param("count")int count);
}
