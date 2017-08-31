package com.ai.crawler;

import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ai.util.Database;
@Component
public class FetchList {
	@Autowired
	DruidPool druidPool;
	
	@Autowired
	ArticleMatchPattern articleMatchPattern;
	
	@Value("${FetchList.FetchCount}")
	int fetchCount;
	
	@Value("${Page.PageTableName}")
	String pageTableName;

	/**
	 * @return 从数据库查询到的需要爬取的页面列表
	 * @说明 查询配置文件中设置个数为 <code>FetchList.FetchCount</code> 个未爬取的页面列表
	 * 网页为目录时，超过三小时爬取一次
	 */
	public List<WebPage> getNextFetchPage() {
		List<WebPage> pageList = new LinkedList<>();
		ResultSet resultSet = null;
		try {
			if (fetchCount > 0) {
				String sql = "select * from "+pageTableName
						+ " where title is null and (FetchingTime is null or timestampdiff(hour,FetchingTime,now())>3)"
						+ " limit " + fetchCount;
				resultSet = druidPool.executeQuery(sql);
				pageList = convertDataSetToPageList(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("FetchList.getNextFetchPage() ERROR:"+e.getMessage());
		}
		finally {
			Database.freeDataResult(resultSet);
		}
		return pageList;
	}

	private List<WebPage> convertDataSetToPageList(ResultSet resultSet){
		List<WebPage> pageList = new LinkedList<>();
		try {
			if (resultSet != null) {
				if(resultSet.first()){				
					if (resultSet.getRow() > 0) {
						while (!resultSet.isAfterLast()) {
							String url = resultSet.getString("url");
							String parentURL = resultSet.getString("parentURL");
							updateFetchingTime(url,parentURL);
							WebPage webPage = new WebPage(url, parentURL);
							String articlePattern=articleMatchPattern.getMatchPattern(url);
							webPage.setArticlePattern(articlePattern);
							pageList.add(webPage);
							resultSet.next();
						}
					}
				}
			}
		}catch (SQLException e) {
			System.err.println("FetchList.convertDataSetToPageList() ERROR:"+e.getMessage());
		}				
		return pageList;
	}

	private void updateFetchingTime(String url,String parentURL){
		try {
			String sql = "update "+pageTableName+" set fetchingTime=now() where url ='"+url+"'";
			druidPool.executeSQL(sql);
		} catch (SQLException e) {
			System.err.println("FetchList.updateFetchingTime() ERROR:"+e.getMessage());
		}
		
	}
}
