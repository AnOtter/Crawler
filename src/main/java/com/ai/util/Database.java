package com.ai.util;

import java.sql.ResultSet;

public class Database {
	public static void freeDataResult(ResultSet resultSet){
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
