package com.ai.crawl2;

import static com.ai.util.DateTime.now;
import static com.ai.util.FileOperator.writeContent;
import java.net.URL;
import java.nio.file.Paths;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author OTTER
 * @类说明 页面爬取之后将内容保存到本地文件
 */
@Configuration
@ConfigurationProperties
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

	private String parserArticle(WebPage fetchedPage) {
		Document document = fetchedPage.getDocument();
		String articleMatchPattern = fetchedPage.getArticlePattern();
		if (document != null && (!articleMatchPattern.equals(""))) {
			Elements articles = document.select("div[" + articleMatchPattern + "]");
			if (articles.size() > 0)
				return articles.get(0).outerHtml();
			else
				return "";
		}
		return "";
	}

	/**
	 * @描述 获取网页地址对应的本地路径
	 * @param url
	 * @param localRootDirectory
	 * @return localFile
	 * @author OTTER
	 * @since 20170430
	 */
	private String getLocalSaveFile(URL url) throws Exception {
		String authority = url.getAuthority();
		String[] dir = authority.split("\\.");
		String domain = "";
		if (dir.length > 1) {
			for (int i = dir.length - 1; i >= 0; i--) {
				domain += dir[i] + "/";
			}
		}
		if (domain.equals("")) {
			domain = authority;
		}
		String path = url.getPath();
		if (path.equals("") || path.endsWith("/"))
			path += "index.html";
		return Paths.get(localSaveDirectory, domain, path).toFile().toString();
	}

	private void saveArticle(WebPage fetchedPage) throws Exception {
		String localFilePath = getLocalSaveFile(new URL(fetchedPage.getUrl()));
		String articleContent = parserArticle(fetchedPage);
		if (!localFilePath.equals("") && !articleContent.equals("")) {
			String content = "<html><head fetchTime=\"" + now() + "\">" + fetchedPage.parserTitle() + "</head><body>"
					+ articleContent + "</body></html>";
			writeContent(localFilePath, content, false, false);
		}
	}

}
