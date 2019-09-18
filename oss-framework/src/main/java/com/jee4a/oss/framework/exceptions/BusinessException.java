package com.jee4a.oss.framework.exceptions;

/**
 * <p>自定义异常类</p> 
 * @author tpeng 2018年2月7日
 * @email 398222836@qq.com
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 响应状态
	 */
	private Integer code;

	public BusinessException() {
		super();
	}
	
	
	public BusinessException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 * @param code
	 * @param message
	 * @param
	 */
	public BusinessException(Integer code, String message) {
		super(message);
		this.code = code;
	}
	
	/**
	 * 获取 响应状态
	 * @return 
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 设置 响应状态
	 * @param 
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
