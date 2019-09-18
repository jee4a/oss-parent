package com.jee4a.oss.auth.common.enums;

public enum RoleStateEnum {
	
	START ((byte) 0, "开启"),
	CLOSE ((byte) 1, "结束");
	private byte key;
	private String value;
	
	RoleStateEnum(byte key, String value) {
		this.key = key;
		this.value = value;
	}

	public byte getKey() {
		return key;
	}


	public String getValue() {
		return value;
	}


}