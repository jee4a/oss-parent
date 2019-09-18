package com.jee4a.oss.framework.net;

import com.jee4a.oss.framework.lang.codec.Md5Utils;

/**
 * 
 * Class Name : ShortUrlGenerator<br>
 * 
 * Description : 短链接生成工具类<br>
 * 
 * @see
 *
 */
public class ShortUrlGenerator {

	/** MD5密匙 */
	private static final String MD5Key = "this is a key";

	private static final String[] chars = new String[] { "a", "b", "c", "d",
			"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3",
			"4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public static void main(String[] args) {
		String sLongUrl = "http://localhost:8080/testTemplate/1423538138051.html";
		String shortUrl = CreateShortUrl(sLongUrl);
		System.out.println("原始链接：" + sLongUrl);
		System.out.println("短链接：" + shortUrl);
	}

	/**
	 * Description ： 生成短链接<br>
	 * 
	 * wumc
	 * 
	 * @param sLongUrl
	 * @return
	 * @since
	 *
	 */
	public static String CreateShortUrl(String sLongUrl) {
		// 对传入网址进行MD5加密
		String sMD5EncryptResult = Md5Utils.getMD5(MD5Key + sLongUrl).toString();
		// 取加密字符的中间8位
		String sTempSubStr = sMD5EncryptResult.substring(7, 15);
		long lhexLong = 0x3FFFFFFF & Long.parseLong(sTempSubStr, 16);

		String outChars = "";
		for (int i = 0; i < 6; i++) {
			// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
			long index = 0x0000003D & lhexLong;
			// 把取得的字符相加
			outChars += chars[(int) index];
			// 每次循环按位右移 5 位
			lhexLong = lhexLong >> 5;
		}

		return outChars;
	}

}
