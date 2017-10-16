package com.ai.crawler;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.crawler.controller.FetchList;

public class FetchListTest extends CrawlerUnitTest {
	@Autowired
	FetchList fetchList;

	@Test
	public void testGetNextFetchNext() {
		assertTrue(fetchList.getNextFetchPages().size() > 0);
	}

}
