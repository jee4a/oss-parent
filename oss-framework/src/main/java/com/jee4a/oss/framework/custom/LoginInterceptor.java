package com.jee4a.oss.framework.custom;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.CommonConstants;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.io.cache.redis.RedisUtils;
import com.jee4a.oss.framework.lang.Assert;
import com.jee4a.oss.framework.lang.DateFormatUtils;
import com.jee4a.oss.framework.lang.JsonUtils;
import com.jee4a.oss.framework.net.servlet.CookieUtils;

public class LoginInterceptor extends BaseController implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * 获取登录信息所用的redis
	 */
	protected RedisUtils redisUtils;
	
	/**
	 * 读取登录信息所需拼接的前缀
	 */
	protected String cacheKeyPrefix ;
	
	/**
	 * 登录有效期,服务端有效期 单位:天 默认一天
	 */
	protected Integer maxAge = 1;

	/**
	 * 缓存内存储的单个用户最大登录信息条数 默认5条
	 */
	protected int maxLen = 5;
	
	/**
	 * 
	 * Description : 登录拦截器
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @since
	 *
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		if (noneLogin(handler)) {
			return true;
		}
		String token = getToken(request);
		LoginInfoVO loginInfoVO = decLoginInfo(token, request);
		if (loginInfoVO != null) {
			return true;
		} else {
			logger.error(getRemoteIp(request) + ":用户未登录");
			response.getWriter().print(new Result<>(Result.SESSION_TIMEOUT, "请登录").toJsonString());
			return false;
		}
	}

	/**
	 * 用户登录信息解密
	 * 
	 * @param ticket
	 * @param request
	 * @return
	 */
	public LoginInfoVO decLoginInfo(String token, HttpServletRequest request) {
		try {
			Assert.isNotBlank(token, -10001, "参数 不能为空");
			DecodedJWT jwt = JWT.decode(token);
			Integer userId = jwt.getClaim(CommonConstants.JWT_PAYLOAD_CLAIMS).asInt() ;
			String key = cacheKeyPrefix + userId ;
			String s =  redisUtils.hget(key, token) ;
			LoginInfoVO loginInfoVO = JsonUtils.fromJson(s, LoginInfoVO.class);
			if (loginInfoVO == null || loginInfoVO.getId() == null) {
				return null ;
			}
			if (loginInfoVO.getExpireTime().getTime() < System.currentTimeMillis()) {
				// 已过期
				redisUtils.hdel(key, token);
			} else {
				// 如果有效期小于最大值的某个系数则续期
				if ((loginInfoVO.getExpireTime().getTime() - System.currentTimeMillis()) < (maxAge * 60 * 60 * 24)  * 0.3) {
					loginInfoVO.setExpireTime(DateFormatUtils.addSec(new Date(), maxAge));
					try {
						redisUtils.hset(key, token, loginInfoVO.toJson());
					} catch (Exception e) {
					}
				}
				request.setAttribute(CommonConstants.LOGIN_INFO, loginInfoVO);
				return loginInfoVO ;
			}
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 重定向至登录
	 * 
	 * @param loginUrl
	 * @param request
	 */
	public void redirectLogin(String loginUrl, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (isAjaxRequest(request)) {
				response.getWriter().print(new Result<>(Result.SESSION_TIMEOUT, "请登录").toJsonString());
			} else {
				response.sendRedirect(loginUrl);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 */
	public boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
			return true;
		}
		return false;
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader(CommonConstants.AUTH_NAME);
		if (token == null) {
			token = CookieUtils.getCookieValue(CommonConstants.AUTH_NAME, request);
		}
		if (token == null) {
			token = request.getParameter(CommonConstants.AUTH_NAME);
		}
		return token;
	}

	public boolean onlyDecLoginInfo(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			LoginInfoAnnotation methodAnnotation = handlerMethod.getMethodAnnotation(LoginInfoAnnotation.class);
			if (methodAnnotation != null) {
				return true;
			}
		}
		return false;
	}

	public boolean noneLogin(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			NoneLoginAnnotation methodAnnotation = handlerMethod.getMethodAnnotation(NoneLoginAnnotation.class);
			if (methodAnnotation != null) {
				return true;
			}
		}
		return false;
	}

	public void setRedisUtils(RedisUtils redisUtils) {
		this.redisUtils = redisUtils;
	}
	public void setCacheKeyPrefix(String cacheKeyPrefix) {
		this.cacheKeyPrefix = cacheKeyPrefix;
	}
}
