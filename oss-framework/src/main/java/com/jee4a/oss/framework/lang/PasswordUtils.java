package com.jee4a.oss.framework.lang;

import com.jee4a.oss.framework.lang.codec.Md5Utils;

/**
 * @description 生成密码
 * @author 398222836@qq.com
 * @date 2015年10月20日
 */

public class PasswordUtils {

	public static final String salt  = "tpeng" ; 
	
	public static String getPassword(String passowrd) {
		return Md5Utils.getMD5(Md5Utils.getMD5(passowrd + salt).reverse().toString()).toString() ;
	}
	
	public static String getPassword(String passowrd , String salt) {
		return Md5Utils.getMD5(Md5Utils.getMD5(passowrd + salt).reverse().toString()).toString() ;
	}
	
	public static void main(String[] args) {
		System.out.println(PasswordUtils.getPassword("123456"));
	}
}
