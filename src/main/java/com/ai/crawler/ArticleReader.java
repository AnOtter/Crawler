package com.ai.crawler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.WebPage;
import static com.ai.util.FileOperator.*;

import java.nio.charset.Charset;

@Component
public class ArticleReader {
	@Value("${Crawler.LocalSaveDirectory}")
	private String localSaveDirectory;

	public String read(WebPage webPage,Charset charset) {
		if (webPage != null) {
			try {
				String pageURL = webPage.getUrl();
				if (!pageURL.equals("")) {
					String localFilePath = getLocalSaveFile(localSaveDirectory, pageURL);
					return readContent(localFilePath,charset);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

}
