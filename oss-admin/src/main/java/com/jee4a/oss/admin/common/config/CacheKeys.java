package com.jee4a.oss.admin.common.config;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jee4a.oss.framework.io.cache.CacheKey;

/**
 * 缓存 key 声明
 */
public class CacheKeys {
	public static final Logger logger = LoggerFactory.getLogger(CacheKeys.class);

	private static final String GLOBEL_KEY = "oss_admin_";

	private static CacheKey build(String key, int exprie) {
		return new CacheKey(GLOBEL_KEY + key, exprie <= 0 ? CacheKey.MINUTES30 : exprie);
	}

	/**
	 * 用户基本信息
	 */
	public static final CacheKey KEY_SYS_USER_ID = build("sys_user_id_", CacheKey.SEC30);

	/**
	 *  验证码
	 */
	public static final CacheKey KEY_KAPTCHA_SESSION_KEY = build("kaptcha_session_id_", CacheKey.MINUTES3);

	/**
	 * 
	 */
	public static final CacheKey KEY_USER_LOGIN_INFO = build("user_login_info_", CacheKey.DAY1);
	/**
	 * 检查cachekeys是否存在重复定义
	 */
	static {
		Field[] fields = CacheKeys.class.getDeclaredFields();
		Set<String> reSet = new HashSet<String>();
		Set<String> tmpSet = new HashSet<String>();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object object = field.get(CacheKeys.class);
				if (object instanceof CacheKey) {
					CacheKey ck = (CacheKey) object;
					for (String string : tmpSet) {
						if (string.startsWith(ck.getKey()) || ck.getKey().startsWith(string)) {
							reSet.add(ck.getKey());
							reSet.add(string);
						}
					}
					tmpSet.add(ck.getKey());
				}
			} catch (IllegalArgumentException e1) {
				logger.error("check cache key error" + e1.getMessage(), e1);
			} catch (IllegalAccessException e1) {
				logger.error("check cache key error" + e1.getMessage(), e1);
			}
		}
		if (!reSet.isEmpty()) {
			System.err.println("CacheKeys 出现混淆定义,请核查:" + reSet);
		}
	}

}
