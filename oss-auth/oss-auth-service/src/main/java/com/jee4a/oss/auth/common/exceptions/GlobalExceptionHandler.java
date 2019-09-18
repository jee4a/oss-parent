package com.jee4a.oss.auth.common.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;

 
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = BusinessException.class)
    public Result<?> BusinessExceptionHandler(HttpServletRequest request, BusinessException e) {
    	 logger.error(e.getMessage(),e);
        return new Result<>(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result<?>  jsonErrorHandler(HttpServletRequest request, Exception e) {
        logger.error(e.getMessage(),e);
    	return new Result<>(Result.DEF_FAIL,"系统异常，稍后重新试");
    }
}
