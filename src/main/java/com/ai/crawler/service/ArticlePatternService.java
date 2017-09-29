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

	public boolean delete(ArticlePattern articlePattern) {
		return articlePatternMapper.delete(articlePattern);
	}

	public List<ArticlePattern> getPatterns() {
		return articlePatternMapper.getPatterns();
	}

	public boolean insert(ArticlePattern articlePattern) {
		return articlePatternMapper.insert(articlePattern);
	}

	public boolean update(ArticlePattern articlePattern) {
		return articlePatternMapper.update(articlePattern);
	}

}
