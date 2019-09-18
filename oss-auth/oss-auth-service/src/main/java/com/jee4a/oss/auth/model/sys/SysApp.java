package com.jee4a.oss.auth.model.sys;

import java.util.Date;

public class SysApp {
    private Integer id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用key(唯一)
     */
    private String appKey;

    /**
     * 应用安全码
     */
    private String appSecret;

    /**
     * 状态   0：禁用  1：启用
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除 0：否；1：是
     */
    private Byte isDelete;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Integer updator;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName 
	 *            应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * @return 应用key(唯一)
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey 
	 *            应用key(唯一)
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    /**
     * @return 应用安全码
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * @param appSecret 
	 *            应用安全码
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
     * @return 状态   0：禁用  1：启用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态   0：禁用  1：启用
     */
    public void setStatus(Byte status) {
        this.status = status;
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
     * @return 是否删除 0：否；1：是
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete 
	 *            是否删除 0：否；1：是
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * @return 创建者
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * @param creator 
	 *            创建者
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
     * @return 更新者
     */
    public Integer getUpdator() {
        return updator;
    }

    /**
     * @param updator 
	 *            更新者
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
        SysApp other = (SysApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
            && (this.getAppKey() == null ? other.getAppKey() == null : this.getAppKey().equals(other.getAppKey()))
            && (this.getAppSecret() == null ? other.getAppSecret() == null : this.getAppSecret().equals(other.getAppSecret()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
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
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getAppKey() == null) ? 0 : getAppKey().hashCode());
        result = prime * result + ((getAppSecret() == null) ? 0 : getAppSecret().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}