package com.jee4a.oss.admin.common.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jee4a.oss.admin.common.interceptor.LoginInterceptor;
import com.jee4a.oss.admin.common.interceptor.OperationInterceptor;
import com.jee4a.oss.admin.service.OperationLogService;
import com.jee4a.oss.framework.io.cache.redis.RedisUtils;

/**
 * @description 拦截器配置
 * @author 398222836@qq.com
 * @date 2018年3月8日
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer   {
	
	@Resource
	private ConfigValues configValues ;
	
	@Resource
	private RedisUtils redisUtils ;
	
	@Resource
	private OperationLogService sysOperationLogManager;

	@Bean(name="loginInterceptor")
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor() ; 
	}
	
	@Bean(name="operationInterceptor")
	public OperationInterceptor operationInterceptor() {
		OperationInterceptor  operationInterceptor = new OperationInterceptor() ;
		operationInterceptor.setSysOperationLogManager(sysOperationLogManager);
		return operationInterceptor ;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(operationInterceptor())
		.addPathPatterns("/**") ;
		
		registry.addInterceptor(operationInterceptor())
		.addPathPatterns("/**") ;
		
	}
	
}
