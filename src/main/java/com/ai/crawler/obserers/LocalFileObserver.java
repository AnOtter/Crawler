package com.ai.crawler.obserers;

import static com.ai.util.DateTime.*;
import static com.ai.util.FileOperator.writeContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ai.crawler.entity.WebPage;
import com.ai.util.FileOperator;

/**
 * @author OTTER
 * @类说明 页面爬取之后将内容保存到本地文件
 */
@Configuration
@ConfigurationProperties
@Scope("prototype")
public class LocalFileObserver implements FetcherObserver {

	@Value("${Crawler.LocalSaveDirectory}")
	private String localSaveDirectory;

	@Override
	public void pageFetched(WebPage webPage) {
		try {
			saveArticle(webPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveArticle(WebPage fetchedPage) throws Exception {
		String localFilePath = FileOperator.getLocalSaveFile(localSaveDirectory, fetchedPage.getUrl());
		String articleContent = fetchedPage.getContent();
		String title = fetchedPage.getTitle();
		if (!localFilePath.equals("") && !articleContent.equals("")) {
			String content = "<html><head fetchTime=\"" + formatDate(fetchedPage.getFetchTime()) + "\">" + title
					+ "</head><body>" + articleContent + "</body></html>";
			writeContent(localFilePath, content, false, false);
		}
	}
}
