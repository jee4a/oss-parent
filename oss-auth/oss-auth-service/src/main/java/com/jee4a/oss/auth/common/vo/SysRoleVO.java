package com.jee4a.oss.auth.common.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jee4a.oss.auth.model.sys.SysRole;

public class SysRoleVO extends SysRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * sub资源
	 */
	private List<SysRoleVO> list;

	// 类别：1：角色ID、2：角色名称、3：创建人、4：最新修改人
	private String queryConditions;
	// 创建时间开始
	private String beginCreateTime;
	// 创建时间结束
	private String endCreateTime;
	// 最新修改时间开始
	private String beginUpdateTime;
	// 最新修改时间结束
	private String endUpdateTime;
	// 角色ID、角色名称、创建人、最新修改人
	private String name;

	// 创建人
	private String createName;
	// 最新修改人
	private String updateName;

	public static SysRoleVO toVO(SysRole model) {
		SysRoleVO v = new SysRoleVO();
		BeanUtils.copyProperties(model, v);
		return v;
	}

	public List<SysRoleVO> getList() {
		return list;
	}

	public void setList(List<SysRoleVO> list) {
		this.list = list;
	}

	public String getQueryConditions() {
		return queryConditions;
	}

	public void setQueryConditions(String queryConditions) {
		this.queryConditions = queryConditions;
	}

	public String getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getBeginUpdateTime() {
		return beginUpdateTime;
	}

	public void setBeginUpdateTime(String beginUpdateTime) {
		this.beginUpdateTime = beginUpdateTime;
	}

	public String getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(String endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

}
