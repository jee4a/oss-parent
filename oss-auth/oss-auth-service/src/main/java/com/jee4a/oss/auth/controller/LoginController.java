package com.jee4a.oss.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jee4a.oss.auth.service.SysLoginService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.custom.LoginInfoVO;
import com.jee4a.oss.framework.custom.NoneLoginAnnotation;

/**
 * @author 398222836@qq.com
 * @date 2018年3月8日
 */
@RestController
public class LoginController extends BaseController {

	@Resource
	private SysLoginService sysLoginService;

	@NoneLoginAnnotation
	@RequestMapping(value = "captcha.jpg", method = RequestMethod.GET)
	public void captcha(String sessionId, HttpServletResponse response) {
		sysLoginService.createCaptcha(sessionId, response);
	}
	/**
	 * 
	 * @param userName (用户名/手机号)
	 * @param password
	 * @param captcha
	 * @param request
	 * @param response
	 * @return
	 */
	@NoneLoginAnnotation
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json" })
	public Result<?> doLogin(String sessionId,String userName, String password, String captcha, 
			@RequestHeader("user-agent") String userAgent, HttpServletResponse response) {
		return sysLoginService.login(captcha, sessionId, userName, password, this.getRemoteIp(), userAgent, response);
	}

	/**
	 * @desc 获取用户登录信息 可用作校验登录状态
	 * @return
	 * @author 39822286@qq.com
	 * @date 2019年7月25日
	 */
	@RequestMapping(value = "/login_info", method = RequestMethod.GET, produces = { "application/json" })
	public Result<LoginInfoVO> loginInfoVO() {
		Result<LoginInfoVO> result = new Result<>() ;
		result.put(getLoginInfo());
		result.setSuccess();
		return result ;
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout() {
		return null;
	}

}
