package com.jee4a.oss.framework.lang;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 是否全部为空
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isAllBlank(String... arr) {
		if (arr == null) {
			return true;
		}
		for (String string : arr) {
			if (isNotBlank(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否全部不为空
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isAllNotBlank(String... arr) {
		if (arr == null) {
			return false;
		}
		for (String string : arr) {
			if (isBlank(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否包含一个为空
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isOrBlank(String... arr) {
		if (arr == null) {
			return true;
		}
		for (String string : arr) {
			if (isBlank(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否包含一个为空
	 *
	 * @param arr
	 * @return
	 */
	public static boolean isBlank(Object arr) {
		if (arr == null) {
			return true;
		}
		if (isBlank(arr.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否包含一个不为空
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean isOrNotBlank(String... arr) {
		if (arr == null) {
			return false;
		}
		for (String string : arr) {
			if (isNotBlank(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串比较
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean strEquals(String a, String b) {
		if (a == null && b == null)
			return true;
		if (a == null)
			return false;
		return a.equals(b);
	}

	/**
	 * 转Long
	 * 
	 * @param s
	 * @return
	 */
	public static Long toLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 转Integer
	 * 
	 * @param s
	 * @return
	 */
	public static Integer toInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * a to z, A to Z
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isAtoZ(String s) {
		if (isEmpty(s)) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' || s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {

			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为中文(部分匹配)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containsChinese(String str) {
		if (str == null) {
			return false;
		}
		for (char c : str.toCharArray()) {
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 统计中文字符数
	 * 
	 * @param str
	 * @return
	 */
	public static int chineseCharCount(String str) {
		int count = 0;
		if (str == null) {
			return count;
		}
		for (char c : str.toCharArray()) {
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 替换emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String replaceEmoji(String source) {
		if (isEmpty(source))
			return source;
		Pattern emoji = Pattern.compile("[^\u4e00-\u9fa50-9a-zA-Z]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher emojiMatcher = emoji.matcher(source);
		boolean result = emojiMatcher.find();
		StringBuffer sb = new StringBuffer();

		while (result) {

			emojiMatcher.appendReplacement(sb, "");
			result = emojiMatcher.find();
		}
		emojiMatcher.appendTail(sb);
		return sb.toString();

	}

	/**
	 * 替换可能存在css注入的字符
	 * 
	 * @param value
	 * @return
	 */
	public static String replcaceXSS(String value) {
		if (isNotEmpty(value)) {
			value = value.trim();
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("<script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	/**
	 * 替换可能存在css注入的字符
	 * 
	 * @param bean
	 * @param clazz
	 * @return
	 */
	public static <T> T replcaceXSSForBean(Object bean, Class<T> clazz) {
		HashMap obj2map = JsonUtils.obj2map(bean);
		for (Object k : obj2map.keySet()) {
			Object v = obj2map.get(k);
			if (v != null && v instanceof String) {
				obj2map.put(k, replcaceXSS(v.toString()));
			}
		}
		return JsonUtils.map2obj(obj2map, clazz);
	}

	/**
	 * @description 手机号码前三后四脱敏
	 * @param mobile
	 * @return
	 * @author 
	 * @date 2019年3月12日
	 */
	public static String 	mobileEncrypt(String mobile) {
		if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
			return mobile;
		}
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * @description 用户名码前三后四脱敏
	 * @param mobile
	 * @return
	 * @date 2019年3月12日
	 */
	public static String nameEncrypt(String name) {
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		return name.replaceAll("^([\\u4e00-\\u9fa5]{1})[\\u4e00-\\u9fa5]*", "$1**");
	}

	/**
	 * @description 用户名脱敏后两位 
	 * @param name
	 * @return
	 * @author 
	 * @date 2019年3月19日
	 */
	public static String rightReplace(String name) {
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		return StringUtils.rightPad(StringUtils.left(name, name.length()-2), name.length(), "*");
	}
	
	/**
	 * @description 身份证前三后四脱敏
	 * @param id
	 * @return
	 * @author 
	 * @date 2019年3月12日
	 */
	public static String idEncrypt(String id) {
		if (StringUtils.isEmpty(id) || (id.length() < 8)) {
			return id;
		}
		return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
	}
	
	/**
	 * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
	 *
	 * @param email
	 * @return
	 */
	public static String email(String email) {
	    if (StringUtils.isBlank(email)) {
	        return "";
	    }
	    int index = StringUtils.indexOf(email, "@");
	    if (index <= 1) {
	        return email;
	    }else{
	        return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
	    }
	}

	/**
	 * 判断是否电话
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(Long mobile) {
		return isMobile("" + mobile);
	}
	public static boolean isMobile(String mobile) {
		if (mobile == null)
			return false;
		return mobile.matches("^1[3|4|5|7|8]\\d{9}$");
	}
	
	public static void main(String[] args) {
		System.out.println(nameEncrypt("留下的"));
	}

}
