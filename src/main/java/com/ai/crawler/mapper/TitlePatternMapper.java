package com.ai.crawler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.crawler.entity.TitlePattern;
@Component
public interface TitlePatternMapper {
	List<TitlePattern> getPatterns();
}
