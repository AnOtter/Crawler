package com.ai.crawler;

import java.net.URL;
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

	@PostConstruct
	public void initlize() throws Exception {
		articleMatchPatterns = new LinkedList<>();
		articleMatchPatterns = articleMatchPatternService.getPatterns();
	}

	public String getMatchPattern(String fetchingURL) {
		try {
			URL url = new URL(fetchingURL);
			String urlAuthority = url.getAuthority();
			for (ArticlePattern articleMatchPattern : articleMatchPatterns) {
				String domain = articleMatchPattern.getAuthority();
				if (urlAuthority.contains(domain))
					return articleMatchPattern.getMatchPattern();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}
}
