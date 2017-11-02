package com.ai.crawler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.crawler.entity.AllowedURL;
@Component
public interface AllowedURLMapper {
	boolean delete(AllowedURL allowedURL);
	List<AllowedURL> getAllowedList();
	boolean insert(AllowedURL allowedURL);
}
