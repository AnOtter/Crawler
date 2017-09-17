package com.ai.crawler;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.TitlePattern;
import com.ai.crawler.service.TitlePatternService;

@Component
public class TitleMatchPatterns {
	@Autowired
	TitlePatternService titlePatternService;

	private List<TitlePattern> titleMatchPatterns;

	/**
	 * @param fetchingURL
	 * @return 页面的标题匹配正则式
	 */
	public List<String> getMatchPatterns(String fetchingURL) {
		List<String> matchPatterns=new LinkedList<>();
		try {
			for (TitlePattern titleMatchPattern : titleMatchPatterns) {
				String authority = titleMatchPattern.getAuthority();
				if (fetchingURL.contains(authority))
				  matchPatterns.add(titleMatchPattern.getMatchPattern());
			}
			return matchPatterns;
		} catch (Exception e) {
			return matchPatterns;
		}
	}

	@PostConstruct
	public void initlize() throws Exception {
		titleMatchPatterns = new LinkedList<>();
		titleMatchPatterns = titlePatternService.getPatterns();
	}

}
