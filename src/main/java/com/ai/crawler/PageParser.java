package com.ai.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;

/**
 * @author OTTER
 * @description 页面解释器
 */
@Component
@Scope("prototype")
public class PageParser {
	@Autowired
	ArticleMatchPatterns articleMatchPatterns;

	@Autowired
	TitleMatchPatterns titleMatchPatterns;

	public void parser(WebPage webPage) {
		parserArticle(webPage);
		parserTitle(webPage);
	}

	/**
	 * @param webPage
	 *            获取webPage的标题
	 *            <p>
	 * 			1.从T_TitlePattern表查找网页标题的匹配正则式，匹配标题
	 *            <p>
	 * 			2.查找 <code>h1</code> 标签的作为页面标题
	 *            <p>
	 * 			3.查找 <code>title</code> 标签的作为页面标题
	 */
	private void parserTitle(WebPage webPage) {
		Document document = webPage.getDocument();
		if (document != null) {
			String titleMatchPattern = titleMatchPatterns.getMatchPattern(webPage.getUrl());
			Elements titles = null;
			if (!titleMatchPattern.equals(""))
				titles = document.select("div[" + titleMatchPattern + "]");
			if (titles == null || titles.size() == 0)
				titles = document.select("h1");
			if (titles == null || titles.size() == 0)
				titles = document.select("title");
			if (titles.size() >= 0) {
				String title = titles.get(0).text();
				webPage.setTitle(title);
			}
		}
	}

	/**
	 * @param webPage
	 * <p>从T_ArticlePattern表查找匹配正则式，匹配文章内容
	 */
	private void parserArticle(WebPage webPage) {
		Document document = webPage.getDocument();
		if (document != null) {
			String articleMatchPattern = articleMatchPatterns.getMatchPattern(webPage.getUrl());
			if (!articleMatchPattern.equals("")) {
				Elements articles = document.select("div[" + articleMatchPattern + "]");
				if (articles.size() > 0) {
					String content = articles.get(0).outerHtml();
					webPage.setContent(content);
				}
			}
		}
	}
}
