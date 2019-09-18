package com.jee4a.oss.admin.common.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;


/**
 * @desc TODO 
 * @author 398222836@qq.com
 * @date 2019年7月26日
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(value = BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException e) {
    	logger.error(e.getMessage(),e);
        return new Result<>(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public void  exceptionHandler(HttpServletResponse response,Exception e) {
    	logger.error(e.getMessage(),e);
    	responseWrite(response, new Result<>(Result.DEF_FAIL,"系统异常，稍后重新试").toJsonString());
    }
}
