package com.jee4a.oss.auth.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jee4a.oss.auth.service.AuthService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.custom.NoneLoginAnnotation;

/**
 * @author 398222836@qq.com
 * @date 2018年3月8日
 */
@RestController
public class AuthController extends  BaseController {

	@Resource
	private AuthService authService;
	 
	@NoneLoginAnnotation
	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = { "application/json" })
	public Result<?> hasPermission(Integer userId,String reqUrl) {
		return  authService.hasPermission(userId, reqUrl) ;
	}
}
