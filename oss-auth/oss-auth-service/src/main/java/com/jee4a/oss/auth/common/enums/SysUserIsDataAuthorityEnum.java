package com.jee4a.oss.auth.common.enums;

public enum SysUserIsDataAuthorityEnum {
	NO((byte)0,"否"),
	YES((byte)1,"是");
	private byte k;
	private String v;
	
	private SysUserIsDataAuthorityEnum(byte key,String value){
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
