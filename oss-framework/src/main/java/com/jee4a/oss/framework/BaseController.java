package com.jee4a.oss.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jee4a.oss.framework.custom.LoginInfoVO;
import com.jee4a.oss.framework.lang.StringUtils;
import com.jee4a.oss.framework.net.IPUtils;

public abstract class BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	public static String redirect(String url) {
		return "redirect:" + url;
	}

	/**
	 * 获取server当前临时目录
	 * 
	 * @return
	 */
	public String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 向response写入字符串
	 * 
	 * @param response
	 * @param str
	 */
	public void responseWrite(HttpServletResponse response, String str) {
		responseWrite(response, str, "text/html; charset=UTF-8", "utf-8");
	}

	/**
	 * 向response写入字符串
	 * 
	 * @param response
	 * @param str
	 * @param contentType
	 * @param enc
	 */
	public void responseWrite(HttpServletResponse response, String str, String contentType, String enc) {
		PrintWriter writer = null;
		try {
			response.setContentType(contentType);
			response.setCharacterEncoding(enc);
			writer = response.getWriter();
			writer.write(str);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * 从request中获取指定属性
	 * 
	 * @param name
	 *            属性名
	 * @return
	 */
	public Object getAttribute(String name) {
		return RequestContextHolder.getRequestAttributes().getAttribute(name, ServletRequestAttributes.SCOPE_REQUEST);
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 获取当前用户的id
	 * 
	 * @return
	 */
	public Integer getUserId() {
		LoginInfoVO loginInfoVO = getLoginInfo();
		return loginInfoVO == null ? null : loginInfoVO.getId();
	}

	public LoginInfoVO getLoginInfo() {
		Object attr = getAttribute(CommonConstants.LOGIN_INFO);
		return attr == null ? null : (LoginInfoVO) attr;
	}

	/*public String getUploadFile() {
		return getRequest().getServletContext().getRealPath("/");
	}*/

	/**
	 * 删除request中指定属性
	 * 
	 * @param name
	 *            属性名
	 */
	public void removeAttribute(String name) {
		RequestContextHolder.getRequestAttributes().removeAttribute(name, ServletRequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 获取客户端版本号
	 * 
	 * @return
	 */
	public String getClientVersion() {
		return getRequest().getHeader("v");
	}

	/***
	 * 绑定属性到request中
	 * 
	 * @param name
	 *            属性名
	 * @param obj
	 *            属性值
	 */
	public void setAttribute(String name, Object obj) {
		RequestContextHolder.getRequestAttributes().setAttribute(name, obj, ServletRequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 解码url参数 utf-8
	 * 
	 * @param str
	 * @return
	 */
	public String urlDecoder(String str) {
		if (str == null) {
			return null;
		}
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 编码url参数 utf-8
	 * 
	 * @param str
	 * @return
	 */
	public String urlEncoder(String str) {
		if (str == null) {
			return null;
		}
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否微信浏览器
	 * 
	 * @return
	 */
	public boolean isWechatClient() {
		HttpServletRequest request = getRequest();
		String browser = request.getHeader("user-agent").toLowerCase();
		;
		if (browser.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		}
		return false;
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

	/**
	 * 重定向至权限提示页
	 * 
	 * @param
	 * @param request
	 */
	public void redirectAuth(String authUrl, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (isAjaxRequest(request)) {
				response.getWriter().print(new Result(Result.NO_AUTH, "没有权限，请联系管理员").toJsonString());
			} else {
				response.sendRedirect(authUrl);
			}
		} catch (Exception e) {
		}
	}

	public String getRootUrl() {
		HttpServletRequest request = getRequest();
		return getRootUrl(request);
	}

	public static String getRootUrl(HttpServletRequest request) {

		String scheme = request.getScheme();
		String serverName = request.getServerName();
		// 80与443协议默认
		String port = request.getServerPort() == 80 ? ""
				: request.getServerPort() == 443 ? "" : ":" + request.getServerPort();
		String cpath = request.getContextPath() == null ? "" : request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append(scheme + "://" + serverName + port + cpath);
		return sb.toString();
	}

	/**
	 * 获取远程访问IP
	 * 
	 * @return
	 */
	public String getRemoteIp() {
		return getRemoteIp(getRequest());
	}

	/**
	 * 获取调用者ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String remoteIp = null;
		remoteIp = request.getHeader("x-forwarded-for");
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("X-Forwarded-For");
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("X-Real-IP");
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("Proxy-Client-IP");
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("HTTP_CLIENT_IP");
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getRemoteAddr();
		}
		if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getRemoteHost();
		}

		/*
		 * 对于获取到多ip的情况下，找到公网ip.
		 */
		String sIP = null;
		if (remoteIp != null && remoteIp.indexOf("unknown") == -1 && remoteIp.indexOf(",") > 0) {
			String[] ipsz = remoteIp.split(",");
			for (int i = 0; i < ipsz.length; i++) {
				if (!IPUtils.internalIp(ipsz[i].trim())) {
					sIP = ipsz[i].trim();
					break;
				}
			}
			/*
			 * 如果多ip都是内网ip，则取第一个ip.
			 */
			if (null == sIP) {
				sIP = ipsz[0].trim();
			}
			remoteIp = sIP;
		}
		if (remoteIp != null && remoteIp.indexOf("unknown") != -1) {
			remoteIp = remoteIp.replaceAll("unknown,", "").trim();
		}
		if (StringUtils.isBlank(remoteIp)) {
			remoteIp = "127.0.0.1";
		}
		return remoteIp;
	}
}
