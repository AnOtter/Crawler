package com.ai.crawler;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.crawler.controller.AllowedURLs;

public class AllowedURLsTest extends CrawlerUnitTest{
	@Autowired
	private AllowedURLs allowedURLs;
	
	@Test
	public void testGetAllowedURLs(){
		assertTrue(allowedURLs.getAllowedURLList().size()>0);
	}
	
}
