package com.jee4a.oss.admin.common.config;

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

	@Value("${portal.captcha.open}")
	private boolean captchaIsOpen;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public boolean isCaptchaIsOpen() {
		return captchaIsOpen;
	}

	public void setCaptchaIsOpen(boolean captchaIsOpen) {
		this.captchaIsOpen = captchaIsOpen;
	}
	 
	
}

