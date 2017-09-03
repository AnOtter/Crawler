package com.ai.crawler;

import java.net.URL;
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

	@PostConstruct
	public void initlize() throws Exception {
		titleMatchPatterns = new LinkedList<>();
		titleMatchPatterns = titlePatternService.getPatterns();
	}
	
	public String getMatchPattern(String fetchingURL){
		try {
			URL url = new URL(fetchingURL);
			String urlAuthority = url.getAuthority();
			for (TitlePattern titleMatchPattern : titleMatchPatterns) {
				String domain = titleMatchPattern.getAuthrity();
				if (urlAuthority.contains(domain))
					return titleMatchPattern.getMatchPattern();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
}
