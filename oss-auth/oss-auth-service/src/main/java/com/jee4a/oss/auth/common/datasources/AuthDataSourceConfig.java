package com.jee4a.oss.auth.common.datasources;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * <p>auth 库数据源配置</p> 
 * 第二个数据源不需要配置@Primary
 * @author tpeng 2018年1月25日
 * @email 398222836@qq.com
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages="com.jee4a.oss.auth.mapper",sqlSessionFactoryRef="authSessionFactory")
public class AuthDataSourceConfig extends AbstractDataSourceConfig{
	
	public  static final Logger logger = LoggerFactory.getLogger(AuthDataSourceConfig.class);
	
	@Value("${is.jndi}")
	private boolean isJndi ;
	
	@Value("${jdbc.url_auth}")
	private String url ;
	
	@Value("${jdbc.username_auth}")
	private String username ;
	
	@Value("${jdbc.pwd_auth}")
	private String password ;
	
	private static final String   MAPPER_LOCATIONS = "mapper/auth/*.xml" ;
	
	@Bean(name="authDS",initMethod="init" ,destroyMethod="close")
	@Primary
	public DataSource  authDataSource() {
		 if(isJndi){
			 if(logger.isDebugEnabled()) {
				 logger.debug("is jndi  !");
			 }
			 JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
	         return (DruidDataSource) dataSourceLookup.getDataSource("java:comp/env/jdbc/jee4a_auth");
		 }
         return super.createDataSource(url, username, password) ;
	}
	
	@Bean(name="authSessionFactory")
	@Primary
	public SqlSessionFactory authSqlSessionFactory() throws Exception {
        return super.createSqlSessionFactory(authDataSource(),ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_LOCATIONS) ;
    }
	
	@Bean(name="authTransactionManager")
	public DataSourceTransactionManager authTransactionManager() {
		return super.createDataSourceTransactionManager(authDataSource()) ;
	}
	
}
