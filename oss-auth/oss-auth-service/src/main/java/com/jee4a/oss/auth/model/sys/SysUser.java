package com.jee4a.oss.auth.model.sys;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysUser {
	private Integer id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String userPwd;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 职务，岗位
	 */
	private Integer positionId;

	/**
	 * 状态 0：禁用 1：正常
	 */
	private Byte state;

	/**
	 * 部门ID
	 */
	private Integer orgId;

	/**
	 * 删除状态 0：否 1：是
	 */
	private Byte isDeleted;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	/**
	 * 创建者ID
	 */
	private Integer creator;

	/**
	 * 更新者ID
	 */
	private Integer updator;

	/**
	 * 用户真实姓名
	 */
	private String actualName;

	/*
	 * 用户坐席号
	 */
	private String staffNo;

	// 最后一次修改密码时间
	private Date lastUpdatePwdTime;
	
	/*
	 * 数据权限
	 */
	private Byte isDataAuthority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/**
	 * @return 盐
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            盐
	 */
	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	/**
	 * @return 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	/**
	 * @return 手机号
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
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
		SysUser other = (SysUser) that;
		return (getId() == null ? other.getId() == null : getId().equals(
				other.getId()))
				&& (getUserName() == null ? other.getUserName() == null
						: getUserName().equals(other.getUserName()))
				&& (getUserPwd() == null ? other.getUserPwd() == null
						: getUserPwd().equals(other.getUserPwd()))
				&& (getSalt() == null ? other.getSalt() == null : getSalt()
						.equals(other.getSalt()))
				&& (getEmail() == null ? other.getEmail() == null : getEmail()
						.equals(other.getEmail()))
				&& (getMobile() == null ? other.getMobile() == null
						: getMobile().equals(other.getMobile()))
				&& (getPositionId() == null ? other.getPositionId() == null
						: getPositionId().equals(other.getPositionId()))
				&& (getState() == null ? other.getState() == null : getState()
						.equals(other.getState()))
				&& (getOrgId() == null ? other.getOrgId() == null : getOrgId()
						.equals(other.getOrgId()))
				&& (getIsDeleted() == null ? other.getIsDeleted() == null
						: getIsDeleted().equals(other.getIsDeleted()))
				&& (getCreateTime() == null ? other.getCreateTime() == null
						: getCreateTime().equals(other.getCreateTime()))
				&& (getUpdateTime() == null ? other.getUpdateTime() == null
						: getUpdateTime().equals(other.getUpdateTime()))
				&& (getCreator() == null ? other.getCreator() == null
						: getCreator().equals(other.getCreator()))
				&& (getUpdator() == null ? other.getUpdator() == null
						: getUpdator().equals(other.getUpdator()))
				&& (getIsDataAuthority() == null ? other.getIsDataAuthority() == null
						: getIsDataAuthority().equals(other.getIsDataAuthority()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getUserName() == null) ? 0 : getUserName().hashCode());
		result = prime * result
				+ ((getUserPwd() == null) ? 0 : getUserPwd().hashCode());
		result = prime * result
				+ ((getSalt() == null) ? 0 : getSalt().hashCode());
		result = prime * result
				+ ((getEmail() == null) ? 0 : getEmail().hashCode());
		result = prime * result
				+ ((getMobile() == null) ? 0 : getMobile().hashCode());
		result = prime * result
				+ ((getPositionId() == null) ? 0 : getPositionId().hashCode());
		result = prime * result
				+ ((getState() == null) ? 0 : getState().hashCode());
		result = prime * result
				+ ((getOrgId() == null) ? 0 : getOrgId().hashCode());
		result = prime * result
				+ ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result
				+ ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result
				+ ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		result = prime * result
				+ ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result
				+ ((getUpdator() == null) ? 0 : getUpdator().hashCode());
		result = prime * result
				+ ((getIsDataAuthority() == null) ? 0 : getIsDataAuthority().hashCode());
		return result;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public Date getLastUpdatePwdTime() {
		return lastUpdatePwdTime;
	}

	public void setLastUpdatePwdTime(Date lastUpdatePwdTime) {
		this.lastUpdatePwdTime = lastUpdatePwdTime;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Byte getIsDataAuthority() {
		return isDataAuthority;
	}

	public void setIsDataAuthority(Byte isDataAuthority) {
		this.isDataAuthority = isDataAuthority;
	}
	
	
}