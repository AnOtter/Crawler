package com.ai.crawler;

import java.util.regex.Pattern;

public class PageURL {
	static final String NORMAL_PAGE_PATTERN = "^(?i)https?://.+(\\.htm|\\.html|\\.shtml)$";
	static final String INDEX_PAGE_PATTERN = "^(?i)https?://.+index(\\.htm|\\.html|\\.shtml)$";

	/**
	 * @param url
	 * @return boolean
	 *         <p>
	 * 		url是目录
	 */
	public static boolean isDirectory(String url) {
		return isIndexPage(url) || (!isNromalPage(url));
	}

	/**
	 * @param url
	 * @return boolean
	 *         <p>
	 * 		url是索引页面
	 */
	private static boolean isIndexPage(String url) {
		return Pattern.matches(INDEX_PAGE_PATTERN, url);
	}

	/**
	 * @param url
	 * @return boolean
	 *         <p>
	 * 		url是普通页面
	 */
	private static boolean isNromalPage(String url) {
		return Pattern.matches(NORMAL_PAGE_PATTERN, url);
	}

}
