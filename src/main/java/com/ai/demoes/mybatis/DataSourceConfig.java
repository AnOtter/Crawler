package com.ai.demoes.mybatis;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "com.ai.demoes.mybatis.mapper", sqlSessionFactoryRef = "webPageSqlSessionFactory")
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
	private static final String MAPPER_PATH = "classpath*:/Mapper/*.xml";

    private static final String ENTITY_PACKAGE = "com.ai.demoes.mybatis.entity";

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

    @Bean(name = "webPageTransactionManager")
    @Primary
    public DataSourceTransactionManager webPageTransactionManager()
            throws SQLException {
        return new DataSourceTransactionManager(webPageDataSource());
    }

    @Bean(name = "webPageSqlSessionFactory")
    @Primary
    public SqlSessionFactory risSqlSessionFactory(
            @Qualifier("webPageDataSource") DataSource webPageDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(webPageDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(MAPPER_PATH));
        sessionFactory.setTypeAliasesPackage(ENTITY_PACKAGE);
        return sessionFactory.getObject();
    }
}
