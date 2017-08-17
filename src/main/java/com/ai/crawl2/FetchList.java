package com.ai.crawl2;

import java.util.List;
import java.sql.ResultSet;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FetchList {
	@Autowired
	DruidPool druidPool;
	@Value("${FetchList.FetchCount}")
	int fetchCount;

	public List<WebPage> getNextFetchPage() throws Exception {
		List<WebPage> pageList = new LinkedList<>();
		ResultSet resultSet = null;
		try {
			if (fetchCount > 0) {
				String sql = "select * from pages where fetchTime is null limit " + fetchCount;
				resultSet = druidPool.executeQuery(sql);
				pageList = convertDataSetToPageList(resultSet);
			}
		} finally {
			freeDataSet(resultSet);
		}
		return pageList;
	}

	private List<WebPage> convertDataSetToPageList(ResultSet resultSet) throws Exception {
		List<WebPage> pageList = new LinkedList<>();
		if (resultSet != null) {
			resultSet.first();
			if (resultSet.getRow() > 0) {
				while (!resultSet.isAfterLast()) {
					String url = resultSet.getString("url");
					String parentURL = resultSet.getString("parentURL");
					WebPage webPage = new WebPage(url, parentURL);
					pageList.add(webPage);
					resultSet.next();
				}
			}
		}
		return pageList;
	}

	private void freeDataSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.getStatement().getConnection().close();
				resultSet.getStatement().close();
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
