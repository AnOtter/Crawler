package com.ai.crawlerweb;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {
	@Test
	public void testHome(){
		App app =new App();
		assertEquals("Welcome to Spring RESTful MVC",app.home());
	}	
}
