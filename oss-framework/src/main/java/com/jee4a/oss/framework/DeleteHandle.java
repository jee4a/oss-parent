package com.jee4a.oss.framework;

public abstract class DeleteHandle {
	// private static Logger logger =
	// LoggerFactory.getLogger(DeleteHandle.class);

	public abstract void cacheDelete();

	public abstract void daoDelete();

	/**
	 * 通用删除实现， 1.删除缓存 <br>
	 * 2.删除数据库<br>
	 * 
	 * 1，2步骤产生的异常向上抛出
	 * 
	 */
	public void execute() {
		cacheDelete();
		daoDelete();
	}
}
