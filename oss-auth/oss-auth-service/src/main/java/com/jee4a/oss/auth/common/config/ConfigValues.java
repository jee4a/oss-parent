package com.jee4a.oss.auth.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 公共配置类
 * 
 * @author tpeng 2018年1月24日
 * @email 398222836@qq.com
 */
@Configuration
public class ConfigValues {
 
	@Value("${spring.application.name}")
	private String appName;

	@Value("${domain.portal.url}")
	private String portalUrl;

	@Value("${oss.login.url}")
	private String loginUrl;

	@Value("${oss.success.url}")
	private String successUrl;

	@Value("${oss.captcha.open}")
	private boolean captchaIsOpen;
	
	 
	public String getAppName() {
		return appName;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public String getPortalUrl() {
		return portalUrl;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public boolean isCaptchaIsOpen() {
		return captchaIsOpen;
	}
}

