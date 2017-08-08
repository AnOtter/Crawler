package com.ai.crawl2;

import static com.ai.util.DateTime.formatDate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebPageService {
	@Autowired
	DruidPool druidPool;

	private boolean isPageExist(WebPage webPage) {
		try{
			String sql="SELECT count(1) as cnt from Pages where url='"+webPage.getUrl()+"'";
			return druidPool.isExist(sql);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean insertPage(WebPage webPage) {
		try {
			String pageTitle = webPage.parserTitle();
			String pageURL = webPage.getUrl();
			String parentURL = webPage.getParentURL();
			Date fetchTime = webPage.getLastFetchTime();
			String sql = "INSERT INTO Pages (url,title,fetchTime,parentURL) values ('"
			+ pageURL + "','" + pageTitle;
			if(fetchTime ==null)
				sql +="',null,'";
			else
				sql +="','" + formatDate(fetchTime) + "','";
			sql+=parentURL + "')";
			return druidPool.executeSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean updatePage(WebPage webPage) {
		return true;
	}

	public boolean savePage(WebPage webPage) {
		if (isPageExist(webPage))
			return updatePage(webPage);
		else
			return insertPage(webPage);
	}
}
