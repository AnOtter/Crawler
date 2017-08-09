package com.ai.crawl2;

import static com.ai.util.DateTime.formatDate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebPageService {
	@Autowired
	DruidPool druidPool;
	@Value("select count(1) as cnt from ${Page.PageTableName} where ${Page.URLFieldName}='")
	String existSQL;

	private boolean isPageExist(WebPage webPage) {
		try{
			String sql=existSQL+webPage.getUrl()+"'";
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
		try {
			String pageTitle = webPage.parserTitle();
			String pageURL = webPage.getUrl();
			String parentURL = webPage.getParentURL();
			Date fetchTime = webPage.getLastFetchTime();
			String sql = "UPDATE Pages SET title = '"+pageTitle+"',parentURL ='"+parentURL+"' ";
			if(fetchTime ==null)
				sql +=",fetchTime=null";
			else
				sql +=",fetchTime='" + formatDate(fetchTime) + "'";
			sql+= " WHERE fetchTime is null and  URL='"+pageURL+"'";
			return druidPool.executeSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean savePage(WebPage webPage) {
		if (isPageExist(webPage))
			return updatePage(webPage);
		else
			return insertPage(webPage);
	}
}
