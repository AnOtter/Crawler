package com.ai.crawler;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TitleMatchPatternsTest extends CrawlerUnitTest {
	@Autowired
	TitleMatchPatterns titleMatchPatterns;
	
	@Test
	public void testGetMatchPattern(){
		String url="http://news.xinhuanet.com/a/3798.html";
		String matchPattern=titleMatchPatterns.getMatchPattern(url);
		assertEquals("class=h-title",matchPattern);
	}
}
