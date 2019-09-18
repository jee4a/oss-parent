package com.jee4a.oss.framework.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 	只解密用户信息,不做强制认证
 * @author tpeng 398222836@qq.com
 *
 */
public @interface LoginInfoAnnotation {

}