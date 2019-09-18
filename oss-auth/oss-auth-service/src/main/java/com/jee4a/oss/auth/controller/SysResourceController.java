package com.jee4a.oss.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jee4a.oss.auth.common.vo.SysResourceVO;
import com.jee4a.oss.auth.service.SysResourceService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.annotation.LogAnnotation;

/**
 * @author 398222836@qq.com
 * @date 2018年3月8日
 */
@RestController
@RequestMapping("/sys/resource")
@SuppressWarnings("rawtypes")
public class SysResourceController extends BaseController {
	
	@Resource
	private SysResourceService  sysResourceService ;
	
	
	/**
	 * 导航菜单
	 */
	@LogAnnotation("导航菜单 ")
	@RequestMapping(value = "/nav" , method = RequestMethod .GET ,produces={"application/json"})
	public Result<List<SysResourceVO>> nav(){
		return sysResourceService.queryUserResourceList(getUserId()) ;
	}
	
	/**
	 * 
	 * @description 查询菜单 
	 * @param id
	 * @return
	 * @author 398222836@qq.com
	 * @date 2018年3月13日
	 */
	@LogAnnotation("菜单详情 ")
	@RequestMapping(value = "/info/{id}" , method = RequestMethod .GET ,produces={"application/json"})
	public Result<SysResourceVO> queryResourceById(@PathVariable Integer id) {
		return sysResourceService.queryById(id) ;
	}
	
	/**
	 * @description 所有菜单列表 
	 * @return
	 * @author 398222836@qq.com
	 * @date 2018年3月13日
	 */
	@LogAnnotation("菜单列表 ")
	@RequestMapping(value = "/list" , method = RequestMethod .GET ,produces={"application/json"})
	public List<SysResourceVO> queryResourceList() {
		return  sysResourceService.queryResourceList(this.getUserId()).get() ;
	}
	
	/**
	 * 
	 * @return
	 * @author 398222836@qq.com
	 * @date 2018年3月13日
	 */
	@LogAnnotation("选择菜单")
	@RequestMapping(value = "/select" , method = RequestMethod .GET ,produces={"application/json"})
	public Result<List<SysResourceVO>>  selectResourceList() {
		return  sysResourceService.selectNotButtonTreeList(this.getUserId())  ;
	}
	
	/**
	 * 都
	 * @description 菜单新增 
	 * @param vo
	 * @return
	 * @author 398222836@qq.com
	 * @date 2018年3月13日
	 */
	@LogAnnotation("新增菜单 ")
	@RequestMapping(value = "/save" , method = RequestMethod.POST ,produces={"application/json"})
	public Result  saveResource(@RequestBody  SysResourceVO vo) {
		return  sysResourceService.saveReource(vo,getUserId()) ;
	}
	
	@LogAnnotation("更新菜单 ")
	@RequestMapping(value = "/update" , method = RequestMethod.POST ,produces={"application/json"})
	public Result  updateResource(@RequestBody  SysResourceVO vo) {
		return  sysResourceService.updateReource(vo, getUserId()) ;
	}
	
	@LogAnnotation("删除菜单 ")
	@RequestMapping(value = "/delete" , method = RequestMethod.POST ,produces={"application/json"})
	public Result  deleteResource(Integer id) {
		return  sysResourceService.deleteReource(id,getUserId()) ;
	}
}
