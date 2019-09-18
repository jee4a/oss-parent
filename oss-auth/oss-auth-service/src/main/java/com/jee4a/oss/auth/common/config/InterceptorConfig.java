package com.jee4a.oss.auth.common.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jee4a.oss.auth.common.interceptor.AuthInterceptor;
import com.jee4a.oss.auth.model.CacheKeys;
import com.jee4a.oss.framework.custom.LoginInterceptor;
import com.jee4a.oss.framework.io.cache.redis.RedisUtils;

/**
 * @description 拦截器配置
 * @author 398222836@qq.com
 * @date 2018年3月8日
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer   {
 
	@Resource
	private RedisUtils redisUtils ;
	
	
	@Bean(name="loginInterceptor")
	public LoginInterceptor loginInterceptor() {
		LoginInterceptor loginInterceptor = new LoginInterceptor() ; 
		loginInterceptor.setRedisUtils(redisUtils);
		loginInterceptor.setCacheKeyPrefix(CacheKeys.KEY_USER_LOGIN_INFO.getKey());
		return new LoginInterceptor() ; 
	}
	
	@Bean(name="authInterceptor")
	public AuthInterceptor authInterceptor() {
		AuthInterceptor  authInterceptor = new AuthInterceptor() ;
		return authInterceptor ;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
		.addPathPatterns("/**") ;
		
		registry.addInterceptor(authInterceptor())
		.addPathPatterns("/**") ;
	}
	
}
