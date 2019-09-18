package com.jee4a.oss.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jee4a.oss.auth.common.vo.SysRoleVO;
import com.jee4a.oss.auth.model.sys.SysRole;
import com.jee4a.oss.auth.service.SysRoleService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.annotation.LogAnnotation;

/**
 * @author 
 * @date 2018年3月13日
 */
@RestController
@RequestMapping("/sys/role")
@SuppressWarnings("rawtypes")
public class SysRoleController extends  BaseController {

	@Resource
	private SysRoleService sysRoleService;

	/**
	 * @description 角色列表
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	@LogAnnotation("角色列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = { "application/json" })
	public Result<List<SysRoleVO>> queryRoleList(
			SysRoleVO sysRoleVO,
			@RequestParam(name = "pageNum", required = true, defaultValue = "0") int pageNum,
			@RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize) {
		return sysRoleService.queryRoleListByPage(sysRoleVO, pageNum, pageSize,this.getUserId());
	}

	/**
	 * @description 保存角色
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	@LogAnnotation("保存角色")
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { "application/json" })
	public Result saveRole(@RequestBody SysRole role) {
		return sysRoleService.addSysRole(role, getUserId());
	}

	/**
	 * @description 角色信息
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	@LogAnnotation("角色信息")
	@RequestMapping(value = "/info/{roleId}", method = RequestMethod.GET, produces = { "application/json" })
	public Result roleInfo(@PathVariable(name = "roleId") Integer roleId) {
		return sysRoleService.queryRole(roleId);
	}

	/**
	 * @description 修改角色
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	@LogAnnotation("修改角色")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json" })
	public Result updateRole(@RequestBody SysRole sysRole) {
		return sysRoleService.updateSysRole(sysRole, getUserId());
	}

	/**
	 * @description 开启角色
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	@LogAnnotation("开启角色")
	@RequestMapping(value = "/open", method = RequestMethod.POST, produces = { "application/json" })
	public Result openOrClose(@RequestBody Integer[] roleIds) {
		return sysRoleService.open(roleIds);
	}

	/**
	 * @description 关闭角色
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	@LogAnnotation("关闭角色")
	@RequestMapping(value = "/close", method = RequestMethod.POST, produces = { "application/json" })
	public Result close(@RequestBody Integer[] roleIds) {
		return sysRoleService.close(roleIds);
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET, produces = { "application/json" })
	public Result queryList(SysRole role) {
		return sysRoleService.queryList(role,this.getUserId());
	}
	
	@RequestMapping(value = "/queryRoleInfo/{userId}", method = RequestMethod.GET, produces = { "application/json" })
	public Result queryRoleInfo(@PathVariable(name = "userId") Integer userId) {
		return sysRoleService.queryRoleInfo(userId);
	}
}
