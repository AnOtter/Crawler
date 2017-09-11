package com.ai.crawler.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author OTTER 数据源 按配置文件生成DataSource返回给Mybatis执行数据库操作
 */
@Configuration
public class DataSourceConfig {
	@Value("${Druid.jdbcDriver}")
	String jdbcDriverName;

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
	private static final String Mapper_Path = "classpath*:/Mapper/*.xml";

	private static final String Entity_Package = "com.ai.crawler.entity";

	@Bean(name = "webPageDataSource")
	@Primary
	public DataSource webPageDataSource() throws SQLException {
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

	@Bean(name = "webPageSqlSessionFactory")
	@Primary
	public SqlSessionFactory webPageSqlSessionFactory(@Qualifier("webPageDataSource") DataSource webPageDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(webPageDataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resolver.getResources(Mapper_Path));
		sessionFactory.setTypeAliasesPackage(Entity_Package);
		return sessionFactory.getObject();
	}
}
