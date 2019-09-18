package com.jee4a.oss.framework;

/**
 * 
 * @param <T>
 */
public abstract class UpdateHandle {
	// private static Logger logger =
	// LoggerFactory.getLogger(UpdateHandle.class);

	public abstract void cacheUpdate();

	public abstract void daoUpdate();

	/**
	 * 通用更新实现， 1.先查数据库 <br>
	 * 2.更新缓存<br>
	 * 
	 * 1，2步骤产生的异常向上抛出，缓存与数据库的结果不是一致性的
	 * 
	 */
	public void execute() {
		daoUpdate();
		cacheUpdate();
	}
}
