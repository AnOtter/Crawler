package com.ai.crawl2;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AllowedURLList extends LinkedList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1998763028281315246L;

	@Value("${Crawler.AllowedURLList}")
	String allowedURLs;

	public LinkedList<String> getAllowedURLList() throws Exception {
		LinkedList<String> allowedURLList = new LinkedList<>();
		if (!allowedURLs.equals("")) {
			String[] urls = allowedURLs.split(";");
			for (String url : urls) {
				if (!url.equals("")) {
					allowedURLList.add(url);
				}
			}
		}
		return allowedURLList;
	}
}
