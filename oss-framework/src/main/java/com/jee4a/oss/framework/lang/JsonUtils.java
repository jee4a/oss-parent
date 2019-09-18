package com.jee4a.oss.framework.lang;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * json工具类,提供常用的工具方法
 * 
 */
public class JsonUtils {
    public static void main(String[] args) {
        JsonUtils.json2Map("{\"a\":1, \"b\":\"111\u000e\"}");
    }
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	/**
	 * 默认的日期格式
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 单例模式
	 */
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	/**
	 * 单例模式
	 */
	public static final XmlMapper XML_MAPPER = new XmlMapper();
	static {
		OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_PATTERN));
		// 设置输出时包含属性的风格
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		// 避免JsonParseException: Illegal unquoted character
		OBJECT_MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ; 
		// OBJECT_MAPPER.addHandler(new DeserializationProblemHandler());
		XML_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_PATTERN));
		// 设置输出时包含属性的风格
		XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		XML_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		XML_MAPPER.registerModule(new JaxbAnnotationModule());
		// XML_MAPPER.addHandler(new DeserializationProblemHandler());
		
	}

	/**
	 * xml字符串序列化成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static <T> T deserializeXML(String str, Class<T> type) {
		try {
			return (T) XML_MAPPER.readValue(str, type);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对象序列化成xml字符串
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String serializeXML(Object object) {
		try {
			return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XML_MAPPER.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转换为json.
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if (null == object) {
			return "";
		}
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (IOException e) {
			logger.error("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 转换为jsonp.
	 * 
	 * @param functionName
	 * @param object
	 * @return
	 */
	public static String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * json转换为对象.
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return OBJECT_MAPPER.readValue(jsonString, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * json字符转换为list
	 * 
	 * @param json
	 * @return
	 */
	public static List json2List(String json) {
		return fromJson(json, ArrayList.class);
	}

	/**
	 * json字符转换为map
	 * 
	 * @param json
	 * @return
	 */
	public static Map json2Map(String json) {
		return fromJson(json, HashMap.class);
	}

	/**
	 * 
	 * @return
	 */
	public static ObjectNode createObjectNode() {
		return OBJECT_MAPPER.createObjectNode();
	}

	/**
	 * 
	 * @return
	 */
	public static ArrayNode createArrayNode() {
		return OBJECT_MAPPER.createArrayNode();
	}

	/**
	 * 
	 * @param jsonString
	 * @param parametrized
	 *            容器类
	 * @param parameterClasses
	 *            容器类泛型参
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> T, Class<?>... parameterClasses) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		return (T) fromJson(jsonString, constructParametricType(T, parameterClasses));
	}

	public static <T> T fromJson(String jsonString, TypeReference<T> type) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return OBJECT_MAPPER.readValue(jsonString, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 * 
	 * @see #constructParametricType(Class, Class...)
	 */
	public static <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return (T) OBJECT_MAPPER.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.error("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * Description ： 将map转换为对象<br>
	 * 
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 * @since
	 *
	 */
	public static <T> T map2obj(Map map, Class<T> clazz) {
		return JsonUtils.fromJson(JsonUtils.toJson(map), clazz);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static HashMap obj2map(Object obj) {
		return JsonUtils.fromJson(JsonUtils.toJson(obj), HashMap.class);
	}

	/**
	 * 構造泛型的Type如List<MyBean>,
	 * 则调用constructParametricType(ArrayList.class,MyBean.class) Map
	 * <String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
	 */
	public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
		return OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	/**
	 * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
	 */
	public static <T> T update(T object, String jsonString) {
		try {
			return (T) OBJECT_MAPPER.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.error("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.error("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}

    public static Map<String,Object> getStringToMap(String str){
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0],str2[1]);
        }
        return map;
    }
}
