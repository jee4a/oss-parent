package com.jee4a.oss.auth.common.enums;

public enum SysUserStateEnum {
	NORMAL((byte)0,"正常"),
	DISABLED((byte)1,"禁用");
	private byte k;
	private String v;
	
	private SysUserStateEnum(byte key,String value){
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
