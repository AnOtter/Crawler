package com.ai.crawler;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleMatchPatternsTest extends CrawlerUnitTest {
	@Autowired
	ArticleMatchPatterns articleMatchPatterns;

	@Test
	public void testGetMatchPattern() {
		String url = "http://news.qq.com/a/20180808/00238.html";
		List<String> matchPaterns = articleMatchPatterns.getMatchPatterns(url);
		assertTrue(matchPaterns.size() > 0);
	}

}
