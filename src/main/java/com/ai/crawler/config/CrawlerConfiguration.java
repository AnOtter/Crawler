package com.ai.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CrawlerConfiguration {
	@Value("${Crawler.OnlyFetchAllowedPages}")
	boolean onlyFetchAllowedPages;

	@Value("${Crawler.SubPageMatchPattern}")
	String subPageMatchPattern;

	@Value("${Crawler.LocalSaveDirectory}")
	private String localSaveDirectory;

	@Value("${Crawler.RawPageSaveDirectory}")
	private String rawPageSaveDirectory;

	/** 本地文件保存目录 */
	public String getLocalSaveDirectory() {
		return localSaveDirectory;
	}

	/** 问解析页面原始文件本地保存目录 */
	public String getRawPageSaveDirectory() {
		return rawPageSaveDirectory;
	}

	/** 子页面网址匹配正则式 */
	public String getSubPageMatchPattern() {
		return subPageMatchPattern;
	}

	/** 是否只爬取允许列表中的页面 */
	public boolean isOnlyFetchAllowedPages() {
		return onlyFetchAllowedPages;
	}

}
