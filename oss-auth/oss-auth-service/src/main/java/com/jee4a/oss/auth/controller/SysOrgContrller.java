package com.jee4a.oss.auth.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jee4a.oss.auth.common.vo.SysOrgVO;
import com.jee4a.oss.auth.model.sys.SysOrg;
import com.jee4a.oss.auth.service.SysOrgService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.annotation.LogAnnotation;

@RequestMapping("/sys/org")
@RestController
@SuppressWarnings("rawtypes")
public class SysOrgContrller extends  BaseController{

	@Resource
	private SysOrgService  sysOrgService;
	
	/**
	 * 
	 * @param org
	 * @return    
	 * @Description: 添加部门
	 * @date:   2018年3月13日 下午9:38:38   
	 * @version V1.0 
	 */

	@LogAnnotation("添加部门信息")
	@RequestMapping(value = "/add",method=RequestMethod.POST,produces={"application/json"})
	public Result addSysOrg(@RequestBody SysOrg org) {
		return sysOrgService.insertOrg(org,this.getUserId());
	}
	
	/**
	 * 
	 * @param org
	 * @return    
	 * @Description: 查找部门列表
	 * @date:   2018年3月13日 下午9:39:29   
	 * @version V1.0 
	 */
	@LogAnnotation("查询部门信息")
	@RequestMapping(value = "/select",method=RequestMethod.GET,produces={"application/json"})
	public Result queryList(SysOrg org) {
		return sysOrgService.queryList(org, this.getUserId());
	}
	
	/**
	 * 
	 * @Description:查询部门上级菜单
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月13日 下午9:39:53   
	 * @version V1.0 
	 */
	@LogAnnotation("查询部门上级菜单")
	@RequestMapping(value = "/info",method=RequestMethod.GET,produces={"application/json"})
	public Result info(){
		return sysOrgService.getOrgInfo(this.getUserId());
	}
	
	/**
	 * 
	 * @Description: 获取部门列表
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月13日 下午9:41:11   
	 * @version V1.0 
	 */
	@LogAnnotation("部门列表")
	@RequestMapping(value = "/list",method=RequestMethod.GET,produces={"application/json"})
	public List<SysOrgVO> getList(String name){
		return sysOrgService.getOrgList(name,this.getUserId()).get();
	}
	
	
	/**
	 * 
	 * @Description: 获取部门详情  
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月14日 下午2:08:17   
	 * @version V1.0 
	 */
	@RequestMapping(value = "/info/{orgId}",method=RequestMethod.GET,produces={"application/json"})
	public Result info(@PathVariable("orgId") Integer orgId){
		return sysOrgService.getOrgById(orgId);
	}
	
	/**
	 * 
	 * @Description:修改部门信息  
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月14日 下午2:08:37   
	 * @version V1.0 
	 */
	@LogAnnotation("修改部门信息")
	@RequestMapping(value = "/update",method=RequestMethod.POST,produces={"application/json"})
	public Result update(@RequestBody SysOrg org){
		org.setUpdator(getUserId());
		return sysOrgService.updateOrg(org);
	}
	
	/**
	 * 
	 * @Description: 删除部门   
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月14日 下午2:08:55   
	 * @version V1.0 
	 */
	@LogAnnotation("删除部门")
	@RequestMapping(value = "/delete",method=RequestMethod.POST,produces={"application/json"})
	public Result delete(Integer id){
		return sysOrgService.updateById(id);
	}
	
	@LogAnnotation("查询所有部门")
	@RequestMapping(value = "/queryOrgList",method=RequestMethod.GET,produces={"application/json"})
	public Result queryOrgList(SysOrg org){
		return sysOrgService.queryOrgList(null);
	}
}
