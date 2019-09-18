package com.jee4a.oss.framework.lang.codec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * md5工具类
 * 
 */
public class Md5Utils {
	private static final Logger logger = LoggerFactory
			.getLogger(Md5Utils.class);
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 */
	public static StringBuffer getMD5(byte[] source) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			md.update(source);
			byte tmp[] = md.digest();
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				sb.append(hexDigits[byte0 >>> 4 & 0xf]);
				sb.append(hexDigits[byte0 & 0xf]);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sb;
	}

	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 */
	public static StringBuffer getMD5(String source) {
		return getMD5(source.getBytes());
	}

	public static void main(String[] args) {
//		Set<String> set = new HashSet<String>();
//		long s = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++) {
//			getMD5(("" + i).getBytes());
//			// set.add(getMD5(("" + i).getBytes()).toString());
//		}
//		System.out.println(System.currentTimeMillis() - s);
//		System.out.println(set.size());
//		System.out.println(getMD5(("1").getBytes()));
	}
}
