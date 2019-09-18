package com.jee4a.oss.admin.model;

import java.util.Date;

public class SysOperationLog {
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long exeTime;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建者ID
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
     * @return 用户操作
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation 
	 *            用户操作
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * @return 请求方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method 
	 *            请求方法
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * @return 请求地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            请求地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * @param params 
	 *            请求参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }
    /**
     * @return IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 
	 *            IP地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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
        SysOperationLog other = (SysOperationLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getOperation() == null ? other.getOperation() == null : this.getOperation().equals(other.getOperation()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getExeTime() == null ? other.getExeTime() == null : this.getExeTime().equals(other.getExeTime()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getOperation() == null) ? 0 : getOperation().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getExeTime() == null) ? 0 : getExeTime().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

	public Long getExeTime() {
		return exeTime;
	}

	public void setExeTime(Long exeTime) {
		this.exeTime = exeTime;
	}
}