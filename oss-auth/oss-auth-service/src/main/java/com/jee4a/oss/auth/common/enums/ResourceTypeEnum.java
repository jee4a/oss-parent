package com.jee4a.oss.auth.common.enums;

/**
 * 
 * @description
 * @author 398222836@qq.com
 * @date 2018年3月12日
 */
public enum ResourceTypeEnum {
	CATALOG((byte)0,"目录"),
	MEHU((byte)1,"菜单"),
	BUTTON((byte)2,"按钮");
	private byte k;
	private String v;
	
	private ResourceTypeEnum(byte key,String value){
		this.k = key;
		this.v = value;
	}
	public byte getK() {
		return k;
	}
	public String getV() {
		return v;
	}
}
