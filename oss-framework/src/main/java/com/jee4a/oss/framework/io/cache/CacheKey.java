package com.jee4a.oss.framework.io.cache;

/**
 * 缓存key 定义
 *
 */
public final class CacheKey {

	/**
	 * 1秒
	 */
	public static final int	SEC1		= 1;

	/**
	 * 3秒
	 */
	public static final int	SEC3		= SEC1 * 3;

	/**
	 * 5秒
	 */
	public static final int	SEC5		= SEC1 * 5;

	/**
	 * 15秒
	 */
	public static final int	SEC15		= SEC1 * 15;

	/**
	 * 30秒
	 */
	public static final int	SEC30		= SEC1 * 30;

	/**
	 * 1分钟
	 */
	public static final int	MINUTES1	= 60 * SEC1;
	
	/**
	 * 3分钟
	 */
	public static final int	MINUTES3	= 60 * SEC3;
	
	/**
	 * 5分钟
	 */
	public static final int	MINUTES5	= 5 * MINUTES1;
	/**
	 * 10分钟
	 */
	public static final int	MINUTES10	= 10 * MINUTES1;
	/**
	 * 30分钟
	 */
	public static final int	MINUTES30	= 30 * MINUTES1;


    public static final int	MINUTES40	= 40 * MINUTES1;

    public static final int	MINUTES50	= 50 * MINUTES1;
	/**
	 * 1小时
	 */
	public static final int	HOUR1		= 60 * MINUTES1;
	/**
	 * 半天
	 */
	public static final int	HOUR12		= 12 * HOUR1;
	/**
	 * 1天
	 */
	public static final int	DAY1		= 24 * HOUR1;

	/**
	 * 3天
	 */
	public static final int	DAY3		= 3 * DAY1;
	/**
	 * 7天
	 */
	public static final int	DAY7		= 7 * DAY1;

	/**
	 * 15天
	 */
	public static final int	DAY15		= 15 * DAY1;

	/**
	 * 30天
	 */
	public static final int	DAY30		= 30 * DAY1;

	/**
	 * 最大值
	 */
	public static final int	MAX			= Integer.MAX_VALUE;

	/**
	 * 超时时间
	 */
	private Integer			expire;

	/**
	 * 缓存 key，通常是一个前缀或后缀
	 */
	private String			key;

	private CacheKey() {
	}

	public CacheKey(String key, Integer expire) {
		this.expire = expire;
		this.key = key;
	}

	public CacheKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return key;
	}

	public String getKey() {
		return key;
	}

	public Integer getExpire() {
		return expire;
	}

	/**
	 * key+appendKey
	 * 
	 * @param appendKey
	 * @return
	 */
	public String getKeyPrefix(Object appendKey) {
		if (appendKey == null) {
			throw new IllegalArgumentException("appendKey 不能为空......");
		}
		return key + appendKey;
	}

	/**
	 * appendKey+key
	 * 
	 * @param appendKey
	 * @return
	 */
	public String getKeySuffix(Object appendKey) {
		if (appendKey == null) {
			throw new IllegalArgumentException("appendKey 不能为空......");
		}
		return appendKey + key;
	}
}
