package com.ai.crawler.obserers;

import static com.ai.util.DateTime.*;
import static com.ai.util.FileOperator.writeContent;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ai.crawler.config.CrawlerConfiguration;
import com.ai.crawler.entity.WebPage;
import com.ai.util.FileOperator;
import com.ai.util.PageURL;

/**
 * @author OTTER
 * @类说明 页面爬取之后将内容保存到本地文件
 */
@Configuration
@ConfigurationProperties
@Scope("prototype")
public class LocalFileObserver implements FetcherObserver {
	@Autowired
	CrawlerConfiguration crawlerConfig;

	private String formatHTMLPage(WebPage fetchedPage) {
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<html><head fetchTime=\"");
		htmlContent.append(formatDate(fetchedPage.getFetchTime()));
		htmlContent.append("\" originalURL=\"");
		htmlContent.append(fetchedPage.getUrl());
		htmlContent.append("\">");
		htmlContent.append(fetchedPage.getTitle());
		htmlContent.append("</head><body>");
		htmlContent.append(fetchedPage.getContent());
		htmlContent.append("</body></html>");
		return htmlContent.toString();
	}

	@Override
	public void pageFetched(WebPage webPage) {
		try {
			String localFilePath = FileOperator.getLocalSaveFile(crawlerConfig.getLocalSaveDirectory(),
					webPage.getUrl());
			String articleContent = webPage.getContent();
			if (!localFilePath.equals("") && !articleContent.equals(""))
				saveParserdPage(webPage, localFilePath);
			else if (!crawlerConfig.getRawPageSaveDirectory().equals(""))
				saveRawPage(webPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveParserdPage(WebPage fetchedPage, String localFilePath) {
		String articleContent = fetchedPage.getContent();
		if (!localFilePath.equals("") && !articleContent.equals("")) {
			String htmlPage = formatHTMLPage(fetchedPage);
			writeContent(localFilePath, htmlPage, false, false);
		}
	}

	private void saveRawPage(WebPage fetchedPage) {
		if (PageURL.isDirectory(fetchedPage.getUrl()))
			return;
		String localFilePath = FileOperator.getLocalSaveFile(crawlerConfig.getRawPageSaveDirectory(),
				fetchedPage.getUrl());
		if (localFilePath.equals(""))
			return;
		Document rawPage = fetchedPage.getDocument();
		if (fetchedPage.getDocument() != null) {
			writeContent(localFilePath, rawPage.toString(), false, false);
		}
	}
}
