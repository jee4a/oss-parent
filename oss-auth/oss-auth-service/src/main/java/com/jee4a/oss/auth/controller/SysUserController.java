package com.jee4a.oss.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jee4a.oss.auth.common.vo.SysUserVO;
import com.jee4a.oss.auth.service.AuthService;
import com.jee4a.oss.auth.service.SysPositionService;
import com.jee4a.oss.auth.service.SysUserService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.annotation.LogAnnotation;
import com.jee4a.oss.framework.custom.NoneLoginAnnotation;



/**
 * <p></p> 
 */
@RestController
@RequestMapping("/sys/user")
@SuppressWarnings("rawtypes")
public class SysUserController  extends  BaseController{
	@Resource
	private SysUserService sysUserService  ;

	@Resource
	private  AuthService authService ;
	 
	@Resource
	private SysPositionService sysPositionService;
	@LogAnnotation("修改密码 ")
	@RequestMapping(value= {"/password"},method=RequestMethod.POST,produces={"application/json"})
	public Result password(String password, String newPassword, String trueNewPassword,HttpServletResponse response){
		return sysUserService.password(password, newPassword,trueNewPassword,getUserId()) ;
	}
	
	@RequestMapping(value= {"info"},method=RequestMethod.GET,produces={"application/json"})
	public Result info(){
		return sysUserService.getUserInfo(this.getUserId());
	}
	
	@LogAnnotation("用户列表列表")
	@RequestMapping(value = "/list" , method = RequestMethod .GET ,produces={"application/json"})
	public Result<PageInfo<SysUserVO>> queryLogList(SysUserVO sysUserVO, @RequestParam(name = "pageNum",required = true,defaultValue = "0") int pageNum,
			@RequestParam(name = "pageSize",required = true,defaultValue = "10") int pageSize) {
		return  sysUserService.queryPage(sysUserVO, pageNum, pageSize,this.getUserId());
	}
	
	/**
	 * 保存用户
	 */
	@LogAnnotation("添加用户")
	@RequestMapping(value = "/save" ,method = RequestMethod .POST ,produces={"application/json"})
	public Result save(@RequestBody SysUserVO user){
		return sysUserService.insertUser(user, this.getUserId());
	}
	
	
	/**
	 * 修改用户
	 */
	@LogAnnotation("修改用户信息")
	@RequestMapping(value="/update",method = RequestMethod.POST ,produces={"application/json"})
	public Result update(@RequestBody SysUserVO user){
		user.setUpdator(this.getUserId());
		return sysUserService.updateUserInfo(user);
	}
	
	@LogAnnotation("获取用户详情")
	@RequestMapping(value = "/getUserInfo/{userId}",method = RequestMethod .GET ,produces={"application/json"})
	public Result getUserInfo(@PathVariable("userId")Integer userId) {
		return sysUserService.getUserInfo(userId);
	}
	
	
	@LogAnnotation("删除用户")
	@RequestMapping(value = "/delete",method = RequestMethod.POST ,produces={"application/json"})
	public Result delete(@RequestBody String userIds) {
		return sysUserService.deleteUser(userIds,this.getUserId());
	}
	

	@RequestMapping(value = "/queryPositionList" , method = RequestMethod.GET ,produces={"application/json"})
	public Result queryPositionList() {
		return sysPositionService.queryList(null);
	}
	
	
	@RequestMapping(value = "/hasPermission" , method = RequestMethod.GET ,produces={"application/json"})
	public Result getUserPerms(Integer userId,String url) {
		return authService.hasPermission(userId, url);
	}
	
	@NoneLoginAnnotation
	@RequestMapping(value= {"/updatePassword"},method=RequestMethod.POST,produces={"application/json"})
	public Result updatePassword(String name,String originalPassword, String newPassword, String trueNewPassword,HttpServletResponse response){
		return  sysUserService.updatePassword(name,originalPassword,newPassword,trueNewPassword);
	}

}
