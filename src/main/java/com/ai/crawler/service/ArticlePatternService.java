package com.ai.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.crawler.entity.ArticlePattern;
import com.ai.crawler.mapper.ArticlePatternMapper;

@Service
public class ArticlePatternService {
	@Autowired
	ArticlePatternMapper articlePatternMapper;

	public List<ArticlePattern> getPatterns() {
		return articlePatternMapper.getPatterns();
	}
}
