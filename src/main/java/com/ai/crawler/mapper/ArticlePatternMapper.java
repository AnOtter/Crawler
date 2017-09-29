package com.ai.crawler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.crawler.entity.ArticlePattern;
@Component
public interface ArticlePatternMapper {
	boolean delete(ArticlePattern articlePattern);
	List<ArticlePattern> getPatterns();
	boolean insert(ArticlePattern articlePattern);
	boolean update(ArticlePattern articlePattern);
}
