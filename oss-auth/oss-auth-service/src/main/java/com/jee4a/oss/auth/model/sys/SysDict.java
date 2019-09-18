package com.jee4a.oss.auth.model.sys;

import java.util.Date;

public class SysDict {
    private Integer id;

    /**
     * 字典名称
     */
    private String dicName;

    /**
     * 字典类型
     */
    private String dicType;

    /**
     * 字典码
     */
    private String dicCode;

    /**
     * 字典值
     */
    private String dicValue;

    /**
     * 排序
     */
    private Integer orderBy;

    /**
     * 备注
     */
    private String remark;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        SysDict other = (SysDict) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDicName() == null ? other.getDicName() == null : this.getDicName().equals(other.getDicName()))
            && (this.getDicType() == null ? other.getDicType() == null : this.getDicType().equals(other.getDicType()))
            && (this.getDicCode() == null ? other.getDicCode() == null : this.getDicCode().equals(other.getDicCode()))
            && (this.getDicValue() == null ? other.getDicValue() == null : this.getDicValue().equals(other.getDicValue()))
            && (this.getOrderBy() == null ? other.getOrderBy() == null : this.getOrderBy().equals(other.getOrderBy()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
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
        result = prime * result + ((getDicName() == null) ? 0 : getDicName().hashCode());
        result = prime * result + ((getDicType() == null) ? 0 : getDicType().hashCode());
        result = prime * result + ((getDicCode() == null) ? 0 : getDicCode().hashCode());
        result = prime * result + ((getDicValue() == null) ? 0 : getDicValue().hashCode());
        result = prime * result + ((getOrderBy() == null) ? 0 : getOrderBy().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
}