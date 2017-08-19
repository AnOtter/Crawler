package com.ai.crawler;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllowedURLList extends LinkedList<String> {
	private static final long serialVersionUID = 1998763028281315246L;

	@Autowired
	DruidPool druidPool;

	public List<String> getAllowedURLList() {
		List<String> allowedURLList = new LinkedList<>();
		ResultSet resultSet = null;
		try {
			String sql = "select * from AllowedURLList";
			try {
				resultSet = druidPool.executeQuery(sql);
				allowedURLList = convertDataSetToPageList(resultSet);
			} catch (Exception e) {
				// TODO: handle exception
			}			
		} finally {
			freeDataSet(resultSet);
		}
		return allowedURLList;
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
