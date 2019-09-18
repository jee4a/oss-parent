package com.jee4a.oss.admin.common.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jee4a.oss.admin.model.SysOperationLog;
import com.jee4a.oss.admin.service.OperationLogService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.annotation.LogAnnotation;
import com.jee4a.oss.framework.io.cache.redis.RedisUtils;
import com.jee4a.oss.framework.lang.JsonUtils;

/**
 * @description 统一操作记录拦截
 * @author 398222836@qq.com
 * @date 2018年3月10日
 */
public class OperationInterceptor extends  BaseController implements HandlerInterceptor {

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	protected OperationLogService sysOperationLogManager;

	@Resource
	protected RedisUtils redisUtils;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		startTime.set(System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		try {
			if (arg2 instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) arg2;
				LogAnnotation methodAnnotation = handlerMethod
						.getMethodAnnotation(LogAnnotation.class);

				String methodName = null;
				String className = null;
				String operation = null;
				if (methodAnnotation != null) {
					operation = methodAnnotation.value();
				}

				methodName = handlerMethod.getMethod().getName();
				className = handlerMethod.getBean().getClass().getName();

				String url = arg0.getServletPath();

				SysOperationLog model = new SysOperationLog();
				//model.setIp(getRemoteIp());
				//model.setCreator(getUserId());
				model.setMethod(className + "." + methodName);
				String params = JsonUtils.toJson(arg0.getParameterMap());
				model.setParams(params.length() > 4800 ? params.substring(0, 4800) : params);
				model.setUrl(url);
				model.setOperation(operation == null ? "未知操作" : operation);
				model.setCreateTime(new Date());
				model.setExeTime(((System.currentTimeMillis()) - startTime.get()));
				StringBuilder sb = new StringBuilder();
				//sb.append("userid:").append(getUserId()).append(",ip:").append(model.getIp()).append(",url:")
				//		.append(url).append(", param:").append(model.getParams());
				if (logger.isDebugEnabled()) {
					logger.debug("------" + sb.toString());
				}

				new Thread(new Runnable() {
					@Override
					public void run() {
						sysOperationLogManager.insertSelective(model);
					}
				}).start();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("time :" + ((System.currentTimeMillis()) - startTime.get()) + " ms");
			}

		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void setSysOperationLogManager(OperationLogService sysOperationLogManager) {
		this.sysOperationLogManager = sysOperationLogManager;
	}

}
