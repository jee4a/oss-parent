package com.jee4a.oss.auth.model.sys;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysRole {
	private Integer id;
	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 部门ID
	 */
	private Integer orgId;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 删除状态 0：否 1：是
	 */
	private Byte isDeleted;

	/**
	 * 创建者ID
	 */
	private Integer creator;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 更新者ID
	 */
	private Integer updator;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 创建人名称
	 */
	private String creatorName;

	/**
	 * 更新人名称
	 */
	private String updatorName;
	/**
	 * 角色菜单列表
	 */
	private List<Integer> menuIdList;
	/**
	 * 机构菜单列表
	 */
	private List<Integer> deptIdList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	/**
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * @return 部门ID
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            部门ID
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return 删除状态 0：否 1：是
	 */
	public Byte getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            删除状态 0：否 1：是
	 */
	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return 创建者ID
	 */
	public Integer getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            创建者ID
	 */
	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	/**
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 更新者ID
	 */
	public Integer getUpdator() {
		return updator;
	}

	/**
	 * @param updator
	 *            更新者ID
	 */
	public void setUpdator(Integer updator) {
		this.updator = updator;
	}

	/**
	 * @return 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Integer> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Integer> menuIdList) {
		this.menuIdList = menuIdList;
	}

	public List<Integer> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<Integer> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdatorName() {
		return updatorName;
	}

	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		SysRole other = (SysRole) that;
		return (getId() == null ? other.getId() == null : getId().equals(
				other.getId()))
				&& (getRoleName() == null ? other.getRoleName() == null
						: getRoleName().equals(other.getRoleName()))
				&& (getRemark() == null ? other.getRemark() == null
						: getRemark().equals(other.getRemark()))
				&& (getOrgId() == null ? other.getOrgId() == null : getOrgId()
						.equals(other.getOrgId()))
				&& (getIsDeleted() == null ? other.getIsDeleted() == null
						: getIsDeleted().equals(other.getIsDeleted()))
				&& (getCreator() == null ? other.getCreator() == null
						: getCreator().equals(other.getCreator()))
				&& (getCreateTime() == null ? other.getCreateTime() == null
						: getCreateTime().equals(other.getCreateTime()))
				&& (getUpdator() == null ? other.getUpdator() == null
						: getUpdator().equals(other.getUpdator()))
				&& (getUpdateTime() == null ? other.getUpdateTime() == null
						: getUpdateTime().equals(other.getUpdateTime()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getRoleName() == null) ? 0 : getRoleName().hashCode());
		result = prime * result
				+ ((getRemark() == null) ? 0 : getRemark().hashCode());
		result = prime * result
				+ ((getOrgId() == null) ? 0 : getOrgId().hashCode());
		result = prime * result
				+ ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result
				+ ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result
				+ ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result
				+ ((getUpdator() == null) ? 0 : getUpdator().hashCode());
		result = prime * result
				+ ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		return result;
	}
}