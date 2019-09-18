package com.jee4a.oss.auth.common.enums;

/**
 * 
 * @author chl
 * 
 *         角色查询类型
 */
public enum SysRoleTypeEnum {
	ROLEID("1", "角色ID"), ROLENAME("2", "角色名称"), CREATENAME("3", "创建人"), UPDATENAME(
			"4", "最新修改人");
	private String k;
	private String v;

	private SysRoleTypeEnum(String key, String value) {
		k = key;
		v = value;
	}

	public String getK() {
		return k;
	}

	public String getV() {
		return v;
	}
}
