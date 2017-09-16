package com.ai.crawler;

import static org.junit.Assert.*;

import org.junit.Test;

public class PageURLTest {
	@Test
	public void testIsDirectory(){
		boolean isDirectory= PageURL.isDirectory("http://www.qq.com");
		assertTrue(isDirectory);
		isDirectory =PageURL.isDirectory("http://news.qq.com/a/20170823/37467.html");
		assertFalse(isDirectory);
	}

}
