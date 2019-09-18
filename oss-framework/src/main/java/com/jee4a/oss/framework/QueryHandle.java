package com.jee4a.oss.framework;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class QueryHandle<T> {
	private static Logger	logger	= LoggerFactory.getLogger(QueryHandle.class);

	private T				result	= null;

	public String			key;

	public int				expire	= Integer.MAX_VALUE;

	public T getResult() {
		return result;
	}

	public abstract T cacheQuery();

	public abstract void cacheSet();

	public abstract T daoQuery();

	/**
	 * 通用查询实现， 1.先查缓存 <br>
	 * 2.缓存未命中则读取DB<br>
	 * 3.将db结果缓存（不缓存null或空集合)<br>
	 * 
	 * 1，3步骤产生的异常不向上抛出，即对外层调用透明
	 * 
	 */
	public T execute() {
		setCacheKey();
		try {
			result = cacheQuery();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (result != null && !(result instanceof Collection && ((Collection) result).isEmpty())
				&& !(result instanceof Map && ((Map) result).isEmpty())) {
			return result;
		}

		result = daoQuery();
		if (result != null) {
			if ((result instanceof Collection && ((Collection) result).isEmpty())
					|| (result instanceof Map && ((Map) result).isEmpty())) {
				return result;
			}
			try {
				cacheSet();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;

	}

	public void setCacheKey() {
	}
}
