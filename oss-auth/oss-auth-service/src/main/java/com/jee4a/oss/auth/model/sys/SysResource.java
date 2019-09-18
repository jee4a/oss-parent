package com.jee4a.oss.auth.model.sys;

import java.util.Date;
import java.util.List;

public class SysResource {
    private Integer id;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String resourceName;
    

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Byte resourceType;

    /**
     * 菜单图标
     */
    private String icon;

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

    private List<Integer> orgIdList;

    public List<Integer> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Integer> orgIdList) {
        this.orgIdList = orgIdList;
    }

    /**
     * 是否显示  0：否   1：是
     */
    private Byte isShow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 父菜单ID，一级菜单为0
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId 
	 *            父菜单ID，一级菜单为0
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return 菜单URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            菜单URL
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return 授权(多个用逗号分隔，如：user:list,user:create)
     */
    public String getPerms() {
        return perms;
    }

    /**
     * @param perms 
	 *            授权(多个用逗号分隔，如：user:list,user:create)
     */
    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }


    /**
     * @return 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon 
	 *            菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
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

    /**
     * @return 是否显示  0：否   1：是
     */
    public Byte getIsShow() {
        return isShow;
    }

    /**
     * @param isShow 
	 *            是否显示  0：否   1：是
     */
    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
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
        SysResource other = (SysResource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getResourceName() == null ? other.getResourceName() == null : this.getResourceName().equals(other.getResourceName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getPerms() == null ? other.getPerms() == null : this.getPerms().equals(other.getPerms()))
            && (this.getResourceType() == null ? other.getResourceType() == null : this.getResourceType().equals(other.getResourceType()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getOrderBy() == null ? other.getOrderBy() == null : this.getOrderBy().equals(other.getOrderBy()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getResourceName() == null) ? 0 : getResourceName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getPerms() == null) ? 0 : getPerms().hashCode());
        result = prime * result + ((getResourceType() == null) ? 0 : getResourceType().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getOrderBy() == null) ? 0 : getOrderBy().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        return result;
    }

	public Byte getResourceType() {
		return resourceType;
	}

	public void setResourceType(Byte resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}