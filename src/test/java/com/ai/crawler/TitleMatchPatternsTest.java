package com.ai.crawler;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.crawler.controller.TitleMatchPatterns;

public class TitleMatchPatternsTest extends CrawlerUnitTest {
	@Autowired
	TitleMatchPatterns titleMatchPatterns;

	@Test
	public void testGetMatchPattern() {
		String url = "http://news.xinhuanet.com/a/3798.html";
		List<String> matchPatterns = titleMatchPatterns.getMatchPatterns(url);
		assertTrue(matchPatterns.size() > 0);
	}
}
