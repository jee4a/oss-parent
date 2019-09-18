package com.jee4a.oss.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @param <T>
 */
public abstract class InsertHandle {
	private static Logger	logger	= LoggerFactory.getLogger(InsertHandle.class);

	/**
	 * 唯一标示
	 */
	private String			uuid;

	public abstract void cacheSet();

	public abstract void daoInsert();


	/**
	 * 通用新增实现， 1.先写入DB <br>
	 * 2.缓存结果<br>
	 * 
	 * 2步骤产生的异常向上抛出
	 * 
	 */
	public void execute() {
		daoInsert();
		try {
			cacheSet();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public <T> T getUuid(Class<T> T) {
		return (T) uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
