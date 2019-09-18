package com.jee4a.oss.auth.model.sys;

import java.util.Date;

public class SysUserLoginLog {
    private Integer id;

    private String userName;

    /**
     * 登录结果 同result里code
     */
    private Integer loginStatus;

    /**
     * 用户id，可为空
     */
    private Integer userId;

    /**
     * 用户登录ip
     */
    private String ip;

    /**
     * 用户客户端信息
     */
    private String clientInfo;

    /**
     * 用户登录时间
     */
    private Date loginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return 登录结果 同result里code
     */
    public Integer getLoginStatus() {
        return loginStatus;
    }

    /**
     * @param loginStatus 
	 *            登录结果 同result里code
     */
    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * @return 用户id，可为空
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId 
	 *            用户id，可为空
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return 用户登录ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 
	 *            用户登录ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * @return 用户客户端信息
     */
    public String getClientInfo() {
        return clientInfo;
    }

    /**
     * @param clientInfo 
	 *            用户客户端信息
     */
    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo == null ? null : clientInfo.trim();
    }

    /**
     * @return 用户登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime 
	 *            用户登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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
        SysUserLoginLog other = (SysUserLoginLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getLoginStatus() == null ? other.getLoginStatus() == null : this.getLoginStatus().equals(other.getLoginStatus()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getClientInfo() == null ? other.getClientInfo() == null : this.getClientInfo().equals(other.getClientInfo()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getLoginStatus() == null) ? 0 : getLoginStatus().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getClientInfo() == null) ? 0 : getClientInfo().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        return result;
    }
}