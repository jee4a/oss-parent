package com.jee4a.oss.auth.api.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jee4a.oss.auth.api.model.UserModel;

public  interface UserApiService  {
	
	@RequestMapping(value = "api/user" ,method = RequestMethod.GET)
	String queryUserById(@RequestParam("id")  Integer id) ;
	
	@RequestMapping(value = "api/user-1" ,method = RequestMethod.GET)
	UserModel queryUserById1(@RequestParam("id")   Integer id) ;
	
}
