package com.jee4a.oss.framework;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jee4a.oss.framework.io.cache.redis.RedisUtils;


/**
 */
public abstract class BaseManager {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	public   RedisUtils  redisUtils ;
}
