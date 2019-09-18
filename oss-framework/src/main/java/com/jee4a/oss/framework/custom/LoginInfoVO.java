package com.jee4a.oss.framework.custom;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jee4a.oss.framework.lang.JsonUtils;

public  class LoginInfoVO implements Serializable{

    private static final long serialVersionUID = 7976374902657376667L;

    /**
	 * 用户id
	 */
	private Integer id;
	 
	/**
	 * 登录时间
	 */
	private Date loginTime;

	/**
	 * 过期时间
	 */
	private Date expireTime;

	/**
	 * 客户端类型
	 */
	private int clientType;

	/**
	 * 登录ip
	 */
	private String ip;

	/**
	 * 客户端登录信息
	 */
	private String clientInfo;
	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户图像
	 */
	private String picture;
	
	/**
	 * 登录token，识别同一个用户多次登录，个性化选择时，用来作为存储key
	 */
	private String token;

	/**
	 * 是否需要刷新信息
	 */
	@JsonIgnore
	private boolean refresh;
	
	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}
	public boolean isRefresh() {
		return refresh;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String toString() {
		return JsonUtils.toJson(this);
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String toJson() {
		return JsonUtils.toJson(this);
	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
 
}
