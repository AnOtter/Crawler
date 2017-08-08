package com.ai.crawl2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author OTTER
 * @类说明 数据库连接池
 * @方法 executeQuery 执行SQL语句返回数据集
 * @方法 executeSQL 执行不需返货结果集的SQL语句
 */
@Configuration
@ConfigurationProperties
public class DruidPool {	
	@Value("${Druid.jdbcDriver}")
	String jdbcDriverName ;  
	@Value("${Druid.dbURL}")
	String dburl;
	@Value("${Druid.DBUserName}")
	String userName;
	@Value("${Druid.DBPassword}")
	String password;
	@Value("${Druid.Filter}")
	String filter;
	@Value("${Druid.MaxActive}")
	int maxActive;
	@Value("${Druid.MaxWait}")
	int maxWait;
	@Value("${Druid.MinIdle}")
	int minIdle;
	@Value("${Druid.TimeBetweenEvictionRunsMillis}")
	int timeBetweenEvictionRunsMillis;
	@Value("${Druid.MinEvictableIdleTimeMillis}")
	int minEvictableIdleTimeMillis;
	@Value("${Druid.TestWhileIdle}")
	boolean testWhileIdle;
	@Value("${Druid.TestOnBorrow}")
	boolean testOnBorrow;
	@Value("${Druid.TestOnReturn}")
	boolean testOnReturn;
	@Value("${Druid.PoolPreparedStatements}")
	boolean poolPreparedStatements;
	@Value("${Druid.MaxOpenPreparedStatements}")
	int maxOpenPreparedStatements;
	@Value("${Druid.ValidationQuery}")
	String validationQuery;	
	
	private DataSource dataSource;
	
	private DataSource getDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriverName);
        dataSource.setUrl(dburl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setFilters(filter);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        dataSource.setRemoveAbandoned(false);
        dataSource.setRemoveAbandonedTimeout(180);
        dataSource.init();
        return dataSource;
    }
	
	private Connection getConnection() throws SQLException{
		if(dataSource==null)
		  dataSource=getDataSource();
		return dataSource.getConnection();
	}
	
	public boolean executeSQL(String sql) throws SQLException{
		Statement statement =null;
		Connection connection =null;
		try {
			connection=getConnection();
			statement =connection.createStatement();
			return statement.execute(sql);
		} finally {	
			if(statement!=null)
				statement.close();
			if(connection!=null)
				connection.close();
		}		
	}	
	
	public ResultSet executeQuery(String sql) throws SQLException{
		Statement statement =null;
		Connection connection =null;
		try {
			connection=getConnection();
			statement =connection.createStatement();
			return statement.executeQuery(sql);
		} finally {	
			if(statement!=null)
				statement.close();
			if(connection!=null)
				connection.close();
		}			
	}
	
	//select count(1) as cnt from 
	public boolean isExist(String sql) throws SQLException{
		Statement statement =null;
		Connection connection =null;
		try {
			connection=getConnection();
			statement =connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.first();
			int selectRows=resultSet.getInt(1);
			return (selectRows>0);
		} finally {	
			if(statement!=null)
				statement.close();
			if(connection!=null)
				connection.close();
		}			
	}
}