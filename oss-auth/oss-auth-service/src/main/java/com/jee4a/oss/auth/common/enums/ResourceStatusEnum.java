package com.jee4a.oss.auth.common.enums;

/**
 * @description 菜单资源状态
 * @author 398222836@qq.com
 * @date 2018年3月12日
 */

public enum ResourceStatusEnum {
	NORMAL((byte)0,"正常"),
	DELETED((byte)1,"删除");
	private byte k;
	private String v;
	
	private ResourceStatusEnum(byte key,String value){
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
