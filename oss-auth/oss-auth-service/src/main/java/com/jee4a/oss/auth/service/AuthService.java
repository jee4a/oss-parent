package com.jee4a.oss.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.mapper.SysResourceMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.model.sys.SysResource;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.lang.Assert;
import com.jee4a.oss.framework.lang.JsonUtils;
import com.jee4a.oss.framework.lang.StringUtils;

/**
 * @desc 权限校验类 
 * @author 398222836@qq.com
 * @date 2019年7月23日
 */
@Service
public class AuthService extends BaseService {
	
	private final PathMatcher pathMatcher = new AntPathMatcher();
	
	@Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysResourceMapper sysResourceMapper;
    
	    
	/**
	 * 检车是否具备该接口权限
	 * 
	 * @param userId
	 * @param url
	 * @return
	 */
	public Result<?> hasPermission(Integer userId,String paramsUrl) {
		Result<?> result = new Result<>();
		try {
			Assert.notNull(userId, -1000, "userId 不能为空");
			Assert.isNotBlank(paramsUrl, -1001, "paramsUrl 不能为空");
			Result<Set<String>> resResult = getAllResource(userId) ;
			if(resResult.isFault()) return result ;
			boolean hasPermission = true ;
			for(String res : resResult.get()) {
				if(pathMatcher.match(res, paramsUrl)) {
					hasPermission = false ;
					break ;
				}
			}
			Assert.isTrue(hasPermission, -4003, "没有权限，请联系管理员");
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	
	 
	
	public Result<Set<String>> getAllResource(Integer userId){
		Result<Set<String>> result = new Result<>();
		List<String> resList = new ArrayList<>();
		
		//系统管理员，拥有最高权限
		if(userId == BaseConstant.SUPER_ADMIN){
			List<SysResource> menuList = sysResourceMapper.selectList(null);
			resList = new ArrayList<>(menuList.size());
			for(SysResource menu : menuList){
				resList.add(menu.getUrl());
			}
		}else{
			resList = sysUserMapper.queryAllResourceUrl(userId);
		}

		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String perms : resList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("resList: "+ JsonUtils.toJson(permsSet));
		}
		result.put(permsSet);
		result.setSuccess();
		return result ;
	}
	
	public static void main(String[] args) {
		PathMatcher pathMatcher = new AntPathMatcher();
		try {
			boolean has =  pathMatcher.match("/get/**", "/get/123/1");
			System.out.println("has =" +  has);
			Assert.isTrue(has,-403, "非法访问，请联系管理员");
		} catch (BusinessException e) {
			System.out.println("b = "+e.getCode());
			System.out.println("b = "+e.getMessage());
		} catch (Exception e) {
			System.out.println("e = "+e.getMessage());
		}
	}
}
