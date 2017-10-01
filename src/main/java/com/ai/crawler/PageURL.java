package com.ai.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageURL {
	static final String Normal_Page_Pattern = "^(?i)https?://.+(\\.htm|\\.html|\\.shtml)$";
	static final String Index_Page_Pattern = "^(?i)https?://.+index(\\.htm|\\.html|\\.shtml)$";
	static final String Publish_Date_Match_Pattern = "(20|19)\\d{2}(/|-)?(0[1-9]|1[012])(/|-)?(0[1-9]|[12]\\d{1}|3[01])";

	private static String formatPublishDate(String publishDate) {
		String formatedDate = publishDate.replaceAll("-", "");
		formatedDate = formatedDate.replaceAll("/", "");
		return formatedDate;
	}

	/**
	 * @param url
	 * @return boolean
	 *         <p>
	 *         url是目录
	 */
	public static boolean isDirectory(String url) {
		return isIndexPage(url) || (!isNromalPage(url));
	}

	/**
	 * @param url
	 * @return boolean
	 *         <p>
	 *         url是索引页面
	 */
	private static boolean isIndexPage(String url) {
		return Pattern.matches(Index_Page_Pattern, url);
	}

	/**
	 * @param url
	 * @return boolean
	 *         <p>
	 *         url是普通页面
	 */
	private static boolean isNromalPage(String url) {
		return Pattern.matches(Normal_Page_Pattern, url);
	}

	/**
	 * @description 获取网址中的发布日期
	 * @param url
	 *            需要匹配发布日期的URL
	 * @return 格式化的URL中发布日期
	 */
	public static String matchPublishDate(String url) {
		String publishDate = "";
		if (!url.equals("")) {
			Pattern pattern = Pattern.compile(Publish_Date_Match_Pattern);
			Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
				publishDate = matcher.group(0);
				publishDate = formatPublishDate(publishDate);
			}
		}
		return publishDate;
	}

}
