package com.ai.crawler;

import static com.ai.util.DateTime.formatDate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class WebPageService {
	@Autowired
	DruidPool druidPool;

	@Value("${Page.PageTableName}")
	String pageTableName;

	private boolean isPageExist(WebPage webPage) {
		try {
			String pageURL = webPage.getUrl().trim();
			String sql = "select count(1) as cnt from " + pageTableName + " where URL='" + pageURL + "'";
			return druidPool.isExist(sql);
		} catch (Exception e) {
			return true;
		}
	}

	private boolean insertPage(WebPage webPage) {
		try {
			String pageURL = webPage.getUrl().trim();
			String parentURL = webPage.getParentURL().trim();
			String sql = "INSERT INTO " + pageTableName + "(url,parentURL) values ('" + pageURL + "'";
			if (parentURL == null)
				sql += ",null)";
			else
				sql += ",'" + parentURL + "')";
			return druidPool.executeSQL(sql);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean updatePage(WebPage webPage) {
		try {
			String pageURL = webPage.getUrl().trim();
			if (!PageURL.isDirectory(pageURL)) {
				String pageTitle = webPage.parserTitle();
				Date fetchTime = webPage.getLastFetchTime();
				if (pageTitle.contains("\'"))
					pageTitle = pageTitle.replaceAll("'", "''");
				if (pageTitle.equals("") && fetchTime == null)
					return false;
				else {
					String sql = "UPDATE " + pageTableName + " SET title = '" + pageTitle + "'";
					if (fetchTime != null)
						sql += ",fetchTime='" + formatDate(fetchTime) + "'";
					sql += " WHERE URL='" + pageURL + "'";
					return druidPool.executeSQL(sql);
				}
			}
			return true;
		} catch (Exception e) {
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
