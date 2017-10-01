package com.ai.crawler.obserers;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.ai.crawler.AllowedURLs;
import static com.ai.crawler.PageURL.*;
import com.ai.crawler.config.CrawlerConfiguration;
import com.ai.crawler.entity.AllowedURL;
import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;

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
	AllowedURLs allowURLs;

	@Autowired
	CrawlerConfiguration crawlerConfig;

	private String subPagePattern;
	private List<AllowedURL> allowedURLList;

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

	@PostConstruct
	public void initAllowedURLList() {
		allowedURLList = allowURLs.getAllowedURLList();
		String subPageMatchPattern = crawlerConfig.getSubPageMatchPattern();
		if (subPageMatchPattern.equals(""))
			subPagePattern = "a[href~=(?i)https?://.+(/|com|cn|org|\\.htm|\\.html|\\.shtml)$]";
		else
			subPagePattern = "a[href~=" + subPageMatchPattern + "]";
	}

	private boolean isURLInAllowList(String subPageURL) {
		if (crawlerConfig.isOnlyFetchAllowedPages() && !subPageURL.equals("")) {
			try {
				URL url = new URL(subPageURL);
				for (AllowedURL allowURL : allowedURLList) {
					if (url.getAuthority().contains(allowURL.getAuthority()))
						return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public void pageFetched(WebPage webPage) {
		List<String> subPageURLs = getSubPageURLs(webPage);
		saveSubPageURLs(subPageURLs, webPage.getUrl());
	}

	private void saveSubPage(WebPage subPage) {
		try {
			webPageService.insert(subPage);
		} catch (DuplicateKeyException e) {
			// 重复键异常很常见 不处理
		} catch (Exception e) {
			System.err.println("saveSubPage ERROR:" + subPage.getUrl());
			System.err.println(e.getMessage());
		}
	}

	private void saveSubPageURLs(List<String> subPageURLs, String pageURL) {
		for (String subPageURL : subPageURLs) {
			if (isURLInAllowList(subPageURL)) {
				WebPage subPage = new WebPage();
				subPage.setUrl(subPageURL);
				subPage.setParentURL(pageURL);
				subPage.setPublishDate(matchPublishDate(subPageURL));
				saveSubPage(subPage);
			}
		}
	}
}
