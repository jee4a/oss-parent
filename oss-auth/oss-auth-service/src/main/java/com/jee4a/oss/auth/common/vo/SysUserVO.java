package com.jee4a.oss.auth.common.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jee4a.oss.auth.model.sys.SysUser;

public class SysUserVO extends SysUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * sub资源
	 */
	private List<SysUserVO> list ;
	
	/**
	 * 父资源名称
	 */
	private String orgName   ;
	
	private List<Integer> roleIdList;
	
	private String positionName;
	
	private String roleName;
	
	private String createName;
	
	private String updateName;
	
	private String beginCreateTime;
	
	private String endCreateTime;
	
	private String beginUpdateTime;
	
	private String endUpdateTime;
	
	private Integer  roleId;
	
	private boolean isResetRole=true;
	
	private List<Integer> orgChildIds;
	
	public static SysUserVO toVO(SysUser model) {
		SysUserVO v = new SysUserVO() ;
		BeanUtils.copyProperties(model, v);
		return v ;
	}

	public static SysUser toModel(SysUserVO vo) {
		SysUser  model = new SysUser() ;
		BeanUtils.copyProperties(vo, model);
		return  model  ;
	}
	
	public List<SysUserVO> getList() {
		return list;
	}


	public void setList(List<SysUserVO> list) {
		this.list = list;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<Integer> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isResetRole() {
		return isResetRole;
	}

	public void setResetRole(boolean isResetRole) {
		this.isResetRole = isResetRole;
	}

	public List<Integer> getOrgChildIds() {
		return orgChildIds;
	}

	public void setOrgChildIds(List<Integer> orgChildIds) {
		this.orgChildIds = orgChildIds;
	}
	
	
	
}
