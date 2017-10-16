package com.ai.crawler.controller;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.ArticlePattern;
import com.ai.crawler.service.ArticlePatternService;

@Component
public class ArticleMatchPatterns {
	@Autowired
	ArticlePatternService articleMatchPatternService;

	private List<ArticlePattern> articleMatchPatterns;

	/**
	 * @param fetchingURL
	 * @return 页面的文章内容匹配正则式
	 */
	public List<String> getMatchPatterns(String fetchingURL) {
		List<String> matchPatterns=new LinkedList<>();
		try {
			for (ArticlePattern articleMatchPattern : articleMatchPatterns) {
				String authority = articleMatchPattern.getAuthority();
				if (fetchingURL.contains(authority))
				  matchPatterns.add(articleMatchPattern.getMatchPattern());
			}
			return matchPatterns;
		} catch (Exception e) {
			return matchPatterns;
		}
	}

	@PostConstruct
	public void initlize() throws Exception {
		articleMatchPatterns = new LinkedList<>();
		articleMatchPatterns = articleMatchPatternService.getPatterns();
	}
}
