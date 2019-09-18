package com.jee4a.oss.auth.model.sys;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysPosition {
	private Integer id;

	/**
	 * 用户名
	 */
	private String positionName;

	/**
	 * 状态 0：启用 1 禁用
	 */
	private Byte state;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		SysPosition other = (SysPosition) that;
		return (getId() == null ? other.getId() == null : getId().equals(
				other.getId()))
				&& (getPositionName() == null ? other.getPositionName() == null
						: getPositionName().equals(other.getPositionName()))
				&& (getState() == null ? other.getState() == null : getState()
						.equals(other.getState()))
				&& (getIsDeleted() == null ? other.getIsDeleted() == null
						: getIsDeleted().equals(other.getIsDeleted()))
				&& (getCreateTime() == null ? other.getCreateTime() == null
						: getCreateTime().equals(other.getCreateTime()))
				&& (getUpdateTime() == null ? other.getUpdateTime() == null
						: getUpdateTime().equals(other.getUpdateTime()))
				&& (getCreator() == null ? other.getCreator() == null
						: getCreator().equals(other.getCreator()))
				&& (getUpdator() == null ? other.getUpdator() == null
						: getUpdator().equals(other.getUpdator()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime
				* result
				+ ((getPositionName() == null) ? 0 : getPositionName()
						.hashCode());
		result = prime * result
				+ ((getState() == null) ? 0 : getState().hashCode());
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
		return result;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}
}