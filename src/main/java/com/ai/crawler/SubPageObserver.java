package com.ai.crawler;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author OTTER
 * @类说明 页面爬取之后获取关联页面地址列表
 */
@Component
@Scope("prototype")
public class SubPageObserver implements FetcherObserver {
	@Autowired
	WebPageService webPageService;

	@Autowired
	AllowedURLList allowURLs;

	@Value("${Crawler.OnlyFetchAllowedPages}")
	boolean onlyFetchAllowedPages;

	/** 子页面网址匹配正则式   */
	@Value("${Crawler.SubPageMatchPattern}")
	String subPageMatchPattern;
	
	private String subPagePattern;
	private List<String> allowedURLList;

	@Override
	public void pageFetched(WebPage webPage) {
		List<String> subPageURLs = getSubPageURLs(webPage);
		saveSubPageURLs(subPageURLs, webPage.getUrl());
	}

	@PostConstruct
	public void initAllowedURLList() {
		allowedURLList = allowURLs.getAllowedURLList();
		if(subPageMatchPattern.equals(""))
			subPagePattern="a[href~=(?i)https?://.+(/|com|cn|org|\\.htm|\\.html|\\.shtml)$]";
		else
			subPagePattern="a[href~=" +subPageMatchPattern+"]";
	}

	/**
	 * @param fetchedPage
	 *            已爬取的页面
	 * @return 已爬取的页面关联的链接列表
	 */
	private List<String> getSubPageURLs(WebPage fetchedPage) {
		List<String> subPageURLs = new LinkedList<>();
		Document document = fetchedPage.getDocument();
		if (document != null) {
			Elements links = document.select(subPagePattern);
			for (Element link : links) {
				subPageURLs.add(link.attr("href"));
			}
		}
		return subPageURLs;
	}

	private void saveSubPageURLs(List<String> subPageURLs, String pageURL) {
		for (String subPageURL : subPageURLs) {
			if (isURLInAllowList(subPageURL)) {
				WebPage subPage = new WebPage(subPageURL, pageURL);
				saveSubPage(subPage);
			}
		}
	}

	private void saveSubPage(WebPage subPage) {
		webPageService.savePage(subPage);
	}

	private boolean isURLInAllowList(String subPageURL) {
		if (onlyFetchAllowedPages) {
			URL url = null;
			try {
				url = new URL(subPageURL);
			} catch (Exception e) {
			}
			if (url != null) {
				String authority = url.getAuthority();
				for (String allowURL : allowedURLList) {
					if (authority.contains(allowURL))
						return true;
				}
			}
			return false;
		}
		return true;
	}
}
