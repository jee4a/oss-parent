package com.jee4a.oss.auth.common.datasources;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;

/**
 * <p>
 * 数据源配置父类
 * </p>
 * 
 * @author tpeng 2018年1月25日
 * @email 398222836@qq.com
 */
public abstract class AbstractDataSourceConfig {

	public  static final Logger logger = LoggerFactory.getLogger(AbstractDataSourceConfig.class);

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.maxActive}")
	private int maxActive;
	@Value("${jdbc.initialSize}")
	private int initialSize;
	@Value("${jdbc.maxWait}")
	private int maxWait;
	@Value("${jdbc.minIdle}")
	private int minIdle;
	@Value("${jdbc.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;
	@Value("${jdbc.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;
	@Value("jdbc.validationQuery}")
	private String validationQuery;
	@Value("${jdbc.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${jdbc.testOnReturn}")
	private boolean testOnReturn;
	@Value("${jdbc.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${jdbc.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${jdbc.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	public DruidDataSource createDataSource(String url, String username, String password) {

		if (StringUtils.isEmpty(url)) {
			logger.info("url is incorrect, please check your profile !");
		}

		DruidDataSource datasource = new DruidDataSource();
		datasource.setDriverClassName(driverClassName);

		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);

		datasource.setMaxActive(maxActive);
		datasource.setInitialSize(initialSize);
		datasource.setMaxWait(maxWait);
		datasource.setMinIdle(minIdle);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

		return datasource;
	}

	public SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String mapperLocations) throws Exception {
		if (StringUtils.isEmpty(mapperLocations)) {
			logger.info("mapperLocations is incorrect, please check your profile !");
		}
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
		return sqlSessionFactoryBean.getObject();
	}

	public DataSourceTransactionManager createDataSourceTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
