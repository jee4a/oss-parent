package com.jee4a.oss.framework.lang;

import javax.annotation.PostConstruct;

public abstract class DaemonThread extends Thread {

	/**
	 * 每次doing后的睡眠时间
	 */
	private long sleepTime = 60 * 1000;

	public DaemonThread() {
		setDaemon(true);
	}

	public DaemonThread(long sleepTime) {
		setDaemon(true);
		setSleepTime(sleepTime);
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		while (true) {
			doing();
			sleep();
		}
	}

	public abstract void doing();

	private void sleep() {
		try {
			Thread.currentThread().sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
