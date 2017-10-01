package com.ai.crawler.obserers;

import static com.ai.util.DateTime.*;
import static com.ai.util.FileOperator.writeContent;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ai.crawler.PageURL;
import com.ai.crawler.config.CrawlerConfiguration;
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
	@Autowired
	CrawlerConfiguration crawlerConfig;

	@Override
	public void pageFetched(WebPage webPage) {
		try {
			String localFilePath = FileOperator.getLocalSaveFile(crawlerConfig.getLocalSaveDirectory(), webPage.getUrl());
			String articleContent = webPage.getContent();
			if (!localFilePath.equals("") && !articleContent.equals(""))
				saveParserdPage(webPage,localFilePath);
			else
				saveRawPage(webPage);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveParserdPage(WebPage fetchedPage,String localFilePath){
		String articleContent = fetchedPage.getContent();
		String title = fetchedPage.getTitle();
		if (!localFilePath.equals("") && !articleContent.equals("")) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<html><head fetchTime=\"");
			stringBuilder.append(formatDate(fetchedPage.getFetchTime()));
			stringBuilder.append("\" originalURL=\"");
			stringBuilder.append(fetchedPage.getUrl());
			stringBuilder.append("\">");
			stringBuilder.append(title);
			stringBuilder.append("</head><body>");
			stringBuilder.append(articleContent);
			stringBuilder.append("</body></html>");
			writeContent(localFilePath, stringBuilder.toString(), false, false);
		}
	}
	
	private void saveRawPage(WebPage fetchedPage){
		if(PageURL.isDirectory(fetchedPage.getUrl()))
			return;
		String localFilePath = FileOperator.getLocalSaveFile(crawlerConfig.getRawPageSaveDirectory(), fetchedPage.getUrl());
		if(localFilePath.equals(""))
			return;
		Document rawPage=fetchedPage.getDocument();
		if(fetchedPage.getDocument() !=null){
			writeContent(localFilePath,rawPage.toString(),false,false);
		}		
	}
}
