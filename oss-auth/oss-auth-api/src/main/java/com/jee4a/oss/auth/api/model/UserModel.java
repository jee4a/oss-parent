package com.jee4a.oss.auth.api.model;

import java.io.Serializable;

public  class UserModel implements Serializable{

    private static final long serialVersionUID = 7976374902657376667L;

    /**
	 * 用户id
	 */
	private Integer id;
	 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
 
}
