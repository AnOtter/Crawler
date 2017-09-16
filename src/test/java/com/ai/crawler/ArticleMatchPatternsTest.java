package com.ai.crawler;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleMatchPatternsTest extends CrawlerUnitTest{
	@Autowired
	ArticleMatchPatterns articleMatchPatterns;
	
	@Test
	public void testGetMatchPattern(){
		String url="http://news.qq.com/a/20180808/00238.html";
		String articleMatchPatern=articleMatchPatterns.getMatchPattern(url);
		assertEquals("id=Cnt-Main-Article-QQ",articleMatchPatern);		
	}

}
