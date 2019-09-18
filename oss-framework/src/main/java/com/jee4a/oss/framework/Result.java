
package com.jee4a.oss.framework;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jee4a.oss.framework.lang.JsonUtils;

/**
 * 统一结果返回输出格式 <br>
 * success 当前请求是否成功，默认悲观 false：即默认值为失败<br>
 * code 错误码 约定为负数类型 ，例如：-9999 <br>
 * message 消息文本，通常用来对 success=false 做进一步描述<br>
 * result 当前业务所需的响应数据<br>
 */
public class Result<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** state 常量 **/
	/** 登陆超时状态 **/
	public static final Integer SESSION_TIMEOUT = -9000;
	
	/** 默认错误状态 **/
	public static final Integer DEF_FAIL = -9999;

	/** 默认权限状态 **/
	public static final Integer NO_AUTH = -403;
	
	/** 参数错误 **/
	public static final Integer PARAMS_ERROR = -2000;

	private boolean success = false;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer code;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object result = null;
	
	public Result() {
	}

	public Result(String error) {
		setError(error);
	}

	public Result(Integer code, String error) {
		setError(code, error);
	}

	public Result(boolean success, Integer code) {
		setSuccess(success);
		setCode(code);
	}

	public Result(boolean success, String error) {
		setSuccess(success);
		setMessage(error);
	}

	public Result(boolean success, Integer code, String error) {
		setError(code, error);
		setSuccess(success);
	}

	public Result(Object obj) {
		setSuccess(success);
		setResult(obj);
	}

	public void setDefaultError() {
		setError(DEF_FAIL, "操作失败,请稍后再试");
	}

	public void setError(String error) {
		setError(DEF_FAIL, error);
	}

	public void setError(Integer code, String error) {
		this.code = code;
		this.message = error;
	}

	private Map toMap() {
		if (result == null) {
			result = new HashMap();
		} else if (!(result instanceof Map)) {
			System.out.println(Result.class.getName() + ":data convert to map");
			result = new HashMap();
		}
		return (Map) result;
	}

	private void toObj(Object obj) {
		if (result == null) {
			result = obj;
		} else if (result instanceof Map) {
			System.out.println(Result.class.getName() + ":data convert to object");
			result = obj;
		} else {
			result = obj;
		}
	}

	/**
	 * 增加属性
	 * 
	 * @param key
	 * @param obj
	 */
	public void put(String key, Object obj) {
		toMap().put(key, obj);
	}

	/**
	 * 设置属性,单例 重复设置将覆盖
	 * 
	 * @param obj
	 */
	public void put(E obj) {
		toObj(obj);
	}

	/**
	 * 追加所有属性
	 * 
	 * @param
	 */
	public void putAll(Map map) {
		if (map != null) {
			toMap().putAll(map);
		}
	}

	/**
	 * 追加所有属性,data值必须为map
	 * 
	 * @param result
	 */
	public void putAll(Result<?> result) {
		Map map = result.get(Map.class);
		putAll(map);
	}

	@JsonIgnore
	public E get() {
		return (E) result;
	}
	/**
	 * 获得属性
	 * 
	 * @param key
	 */
	@JsonIgnore
	public Object get(String key) {
		if (isDataMap()) {
			return ((Map) result).get(key);
		}
		return null;
	}

	@JsonIgnore
	public <T> T get(Class<T> clazz) {
		return (T) result;
	}

	/**
	 * 获得属性
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	@JsonIgnore
	public <T> T get(String key, Class<T> type) {
		return (T) get(key);
	}

	@JsonIgnore
	public boolean isDataMap() {
		if (result != null && result instanceof Map) {
			return true;
		}
		return false;
	}

	@JsonIgnore
	public boolean isNULL() {
		return result == null;
	}

	/**
	 * 判断result是否存在键值
	 * 
	 * @return
	 */
	@JsonIgnore
	public boolean isEmpty() {
		if (result == null) {
			return true;
		} else if (result instanceof Map && ((Collection) result).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 删除属性
	 * 
	 * @param key
	 */
	@JsonIgnore
	public Object remove(String key) {
		if (isDataMap()) {
			return ((Map) result).remove(key);
		}
		return null;
	}

	@JsonIgnore
	public boolean isFault() {
		return !isSuccess();
	}

	@JsonIgnore
	public void setFail() {
		this.success = false;
		if (this.code == null) {
			this.code = DEF_FAIL;
		}
	}

	/**
	 * 当前是否为错误状态，当state被设置为小于0的值
	 * 
	 * @return
	 */
	public boolean hasError() {
		if (code != null && code <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否包含对应code
	 * 
	 * @param _code
	 * @return
	 */
	public boolean hasCode(int _code) {
		if (code != null && code == _code) {
			return true;
		}
		return false;
	}

	public boolean canNext() {
		return !hasError();
	}

	/**
	 * 获取状态信息
	 * 
	 * @param error
	 */
	public void fromError(Result<?> error) {
		if(error == null){
            return;
        }

		this.code = error.code;
		setSuccess(error.isSuccess());
		this.message = error.message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		
		this.success = success;
	}

	public void setSuccess(String message) {
		this.message = message;
		setSuccess();
	}

	public void setSuccess(Map<String, Object> map) {
		putAll(map);
		setSuccess();
	}

	public void setSuccess() {
		this.success = true;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String toJsonString() {
		return JsonUtils.toJson(this);
	}

	@JsonIgnore
	public Map getOutPutMap() {
		Map map = new HashMap();
		map.put("success", this.success);
		if (this.message != null) {
			map.put("message", this.message);
		}
		if (this.code != null) {
			map.put("code", this.code);
		}

		if (this.result != null) {
			map.put("result", result);
		}
		return map;
	}
	public void print() {
		System.out.println(toJsonString());
	}

}
