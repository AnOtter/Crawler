package com.ai.crawler;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

	public void parse(WebPage webPage) {
		parserArticle(webPage);
		parserTitle(webPage);
	}

	private String parseContent(Elements article) {
		if (article != null && article.size() > 0) {
			Elements lines = article.get(0).select("p");
			if (lines.size() > 0) {
				StringBuilder content = new StringBuilder();
				for (Element line : lines) {
					content.append("<p>");
					content.append(line.text());
					content.append("</p>");
				}
				if (content.length() > 0) {
					content.insert(0, "<div>");
					content.append("</div>");
				}
				return content.toString();
			} else {
				return article.toString();
			}
		}
		return "";
	}

	/**
	 * @param webPage
	 *            <p>
	 *            从T_ArticlePattern表查找匹配正则式，匹配文章内容
	 */
	private void parserArticle(WebPage webPage) {
		Document document = webPage.getDocument();
		if (document != null) {
			List<String> matchPatterns = articleMatchPatterns.getMatchPatterns(webPage.getUrl());
			for (String matchPattern : matchPatterns) {
				if (!matchPattern.equals("")) {
					Elements articles = document.select("div[" + matchPattern + "]");
					String content = parseContent(articles);
					if (!content.equals("")) {
						webPage.setContent(content);
						break;
					}
				}
			}
		}
	}

	/**
	 * @param webPage
	 *            获取webPage的标题
	 *            <p>
	 *            1.从T_TitlePattern表查找网页标题的匹配正则式，匹配标题
	 *            <p>
	 *            2.查找 <code>h1</code> 标签的作为页面标题
	 *            <p>
	 *            3.查找 <code>title</code> 标签的作为页面标题
	 */
	private void parserTitle(WebPage webPage) {
		Document document = webPage.getDocument();
		if (document != null) {
			List<String> matchPatterns = titleMatchPatterns.getMatchPatterns(webPage.getUrl());
			Elements titles = null;
			for (String titleMatchPattern : matchPatterns) {
				if (!titleMatchPattern.equals("")) {
					titles = document.select("div[" + titleMatchPattern + "]");
					if (titles != null && titles.size() > 0)
						break;
				}
			}
			if (titles == null || titles.size() == 0)
				titles = document.select("h1");
			if (titles == null || titles.size() == 0)
				titles = document.select("title");
			if (titles.size() > 0) {
				String title = titles.get(0).text();
				webPage.setTitle(title);
			}
		}
	}
}
