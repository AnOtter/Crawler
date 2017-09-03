package com.ai.crawler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;

@Component
public interface WebPageMapper {
	List<WebPage> getFetchList(int count);
	
	boolean updateFetchingTime(WebPage webPage);
	
	int existCount(WebPage webPage);
	
	boolean insert(WebPage webPage);
	
	boolean update(WebPage webPage);
}
