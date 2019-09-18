package com.jee4a.oss.admin.common.enums;

public enum SysDeletedEnum {
	NORMAL ((byte) 0, "正常"),
	DELETED((byte) 1, "删除");
	private byte k;
	private String v;

	private SysDeletedEnum(byte key, String value) {
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
