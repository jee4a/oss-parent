package com.jee4a.oss.framework.lang;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.jee4a.oss.framework.exceptions.BusinessException;

/**
 *  服务层参数校验类
 * @description
 * @author 398222836@qq.com
 */
public class Assert  {

	/**
	 * @description 是否为真 
	 * @param expression
	 * @param message
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new BusinessException(message);
		}
	}
	
	/**
	 * @description 是否为真  
	 * @param expression
	 * @param code
	 * @param message
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isTrue(boolean expression, Integer code ,String message) {
		if (!expression) {
			throw new BusinessException(code,message);
		}
	}
	
	
	/**
	 * @description 是否为空  
	 * @param object
	 * @param message
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new BusinessException(message);
		}
	}
	
	/**
	 * @description 是否为空   
	 * @param object
	 * @param code
	 * @param message
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isNull(Object object, Integer code ,String message) {
		if (object != null) {
			throw new BusinessException(code,message);
		}
	}
	
	/**
	 * @description 是否不为空   
	 * @param object
	 * @param message
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new BusinessException(message);
		}
	}

	/**
	 * @description 是否不为空    
	 * @param object
	 * @param code
	 * @param message
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void notNull(Object object,Integer code , String message) {
		if (object == null) {
			throw new BusinessException(code,message);
		}
	}
	
	public static void isBlank(String str, Integer code ,String message) {
		if(!StringUtils.isBlank(str)) {
			throw new BusinessException(code,message);
		}
	}
	
	public static void isNotBlank(String str, Integer code ,String message) {
		if(!StringUtils.isNotBlank(str)) {
			throw new BusinessException(code,message);
		}
	}
	
	/**
	 * @description 是否全部为空 
	 * @param code
	 * @param message
	 * @param arr
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isAllBlank(Integer code ,String message,String... arr) {
		if(!StringUtils.isAllBlank(arr)) {
			throw new BusinessException(code,message);
		}
	}
	
	/**
	 * @description 是否全部不为空
	 * @param code
	 * @param message
	 * @param arr
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isAllNotBlank(Integer code ,String message,String... arr) {
		if(!StringUtils.isAllNotBlank(arr)) {
			throw new BusinessException(code,message);
		}
	}
	
	/**
	 * @description  是否包含一个为空
	 * @param code
	 * @param message
	 * @param arr
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isOrBlank(Integer code ,String message,String... arr) {
		if(!StringUtils.isOrBlank(arr)) {
			throw new BusinessException(code,message);
		}
	}
	
	/**
	 * @description  是否包含一个不为空
	 * @param code
	 * @param message
	 * @param arr
	 * @author 398222836@qq.com
	 * @date 2019年7月1日
	 */
	public static void isOrNotBlank(Integer code ,String message,String... arr) {
		if(!StringUtils.isOrNotBlank(arr)) {
			throw new BusinessException(code,message);
		}
	}
	
	
	public static void isEmpty(String str, Integer code ,String message) {
		if(StringUtils.isNotEmpty(str)) {
			throw new BusinessException(code,message);
		}
	}
	
	public static void isNotEmpty(String str, Integer code ,String message) {
		if(StringUtils.isEmpty(str)) {
			throw new BusinessException(code,message);
		}
	}
	
	
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new BusinessException(message);
		}
	}
	
	public static void notEmpty(Collection<?> collection, Integer code , String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new BusinessException(code,message);
		}
	}
	
	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new BusinessException(message);
		}
	}
	
	public static void notEmpty(Map<?, ?> map, Integer code , String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new BusinessException(code,message);
		}
	}
}

