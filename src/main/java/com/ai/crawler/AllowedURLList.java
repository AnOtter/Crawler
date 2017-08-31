package com.ai.crawler;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.util.Database;

@Component
public class AllowedURLList extends LinkedList<String> {
	private static final long serialVersionUID = 1998763028281315246L;
	
	private List<String> allowedURLList;
	
	public List<String> getAllowedURLList() {
		return allowedURLList;
	}

	public void setAllowedList(List<String> allowedURLList) {
		this.allowedURLList = allowedURLList;
	}

	@Autowired
	DruidPool druidPool;
	
	@PostConstruct
	public void initlizeAllowedList() {
		allowedURLList = new LinkedList<>();
		ResultSet resultSet = null;
		try {
			String sql = "select * from T_AllowedURLList";
			try {
				resultSet = druidPool.executeQuery(sql);
				allowedURLList = convertDataSetToPageList(resultSet);
			} catch (Exception e) {
				// TODO: handle exception
			}			
		} finally {
			Database.freeDataResult(resultSet);
		}
	}
	
	private List<String> convertDataSetToPageList(ResultSet resultSet) throws Exception {
		List<String> allowList = new LinkedList<>();
		if (resultSet != null) {
			resultSet.first();
			if (resultSet.getRow() > 0) {
				while (!resultSet.isAfterLast()) {
					String url = resultSet.getString("AllowedURL");
					allowList.add(url);
					resultSet.next();
				}
			}
		}
		return allowList;
	}
}
