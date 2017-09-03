package com.ai.crawler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.crawler.entity.ArticlePattern;
@Component
public interface ArticlePatternMapper {
	List<ArticlePattern> getPatterns();
}
