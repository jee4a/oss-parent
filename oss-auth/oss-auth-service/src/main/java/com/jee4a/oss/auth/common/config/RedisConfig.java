package com.jee4a.oss.auth.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jee4a.oss.framework.io.cache.redis.RedisUtils;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @description 缓存配置
 * @author 398222836@qq.com
 * @date 2018年3月16日
 */
@Configuration
public class RedisConfig {

	@Value("${redis.url}")
	private String uri;
	@Value("${redis.pool.maxTotal}")
	private int maxTotal;
	@Value("${redis.pool.minIdle}")
	private int minIdle;
	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${redis.pool.maxWait}")
	private long maxWaitMillis;
	@Value("${redis.pool.testOnReturn}")
	private boolean testOnReturn;
	@Value("${redis.pool.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${redis.pool.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;
	
	
	@Bean(name = "redisUtils")
	public RedisUtils redisUtils() {
		RedisUtils redisUtils = new RedisUtils() ;
		redisUtils.setUri(uri);
		redisUtils.setJedisPoolConfig(jedisPoolConfig());
		return redisUtils ;
	}
	
	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnReturn(testOnReturn);
		jedisPoolConfig.setTestWhileIdle(testWhileIdle);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		return new JedisPoolConfig();
	}
}
