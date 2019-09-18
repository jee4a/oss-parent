package com.jee4a.oss.framework.lang;

import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

public class UUIDGenerator {
	private static final char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * Description ： 获得一个UUID<br>
	 * 
	 * wumc
	 * 
	 * @return
	 * @since
	 *
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		char[] charArray = s.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : charArray) {
			if (c != '-') {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取一个短码UUID,长度越短重复可能性越大.业务自行进行去重处理 <br>
	 * 5位生产100w,预计重复率万分之五 <br>
	 * 6位生产100w,预计重复率十万分之一 <br>
	 * 
	 * @param len
	 * @return
	 */
	public static String getShortUUID(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(chars[RandomUtils.nextInt(0,chars.length-1)]);
		}
		return sb.toString();
	}

	/**
	 * Description ： 获得指定数目的UUID<br>
	 * 
	 * wumc
	 * 
	 * @param number
	 * @return
	 * @since
	 *
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	
}
