package com.ai.crawler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.crawler.entity.TitlePattern;
@Component
public interface TitlePatternMapper {
	boolean delete(TitlePattern titlePattern);
	List<TitlePattern> getPatterns();
	boolean insert(TitlePattern titlePattern);
	boolean update(TitlePattern titlePattern);
}
