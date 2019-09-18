package com.jee4a.oss.auth.model.sys;

import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

public class SysOrg {
    private Integer id;

    /**
     * 上级部门ID，一级部门为0
     */
    private Integer parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer orderBy;

    /**
     * 删除状态  0：否   1：是
     */
    private Byte isDeleted;

    /**
     * 创建者ID
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者ID
     */
    private Integer updator;

    /**
     * 更新时间
     */
    private Date updateTime;

    private List<Integer> idList;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 上级部门ID，一级部门为0
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId 
	 *            上级部门ID，一级部门为0
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    /**
     * @return 排序
     */
    public Integer getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy 
	 *            排序
     */
    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return 删除状态  0：否   1：是
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted 
	 *            删除状态  0：否   1：是
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
        SysOrg other = (SysOrg) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getOrderBy() == null ? other.getOrderBy() == null : this.getOrderBy().equals(other.getOrderBy()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getOrderBy() == null) ? 0 : getOrderBy().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}