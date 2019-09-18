package com.jee4a.oss.auth.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jee4a.oss.auth.model.sys.SysDict;
import com.jee4a.oss.auth.service.SysDictService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.annotation.LogAnnotation;

@RestController
@RequestMapping("/sys/dict")
@SuppressWarnings("rawtypes")
public class SysDictController extends  BaseController{
	@Resource
	private SysDictService sysDictService;
	@LogAnnotation("字典添加")
	@RequestMapping(value = "/addDict" , method = RequestMethod.POST ,produces={"application/json"})
	public Result addDict(@RequestBody SysDict dict) {
		dict.setCreator(this.getUserId());
		return sysDictService.addDict(dict);
	}
	
	
	@LogAnnotation("字典列表")
	@RequestMapping(value = "/queryPage" , method = RequestMethod.GET ,produces={"application/json"})
	public Result queryPage(@RequestParam(name = "dictName",required = false )String dictName,@RequestParam(name = "pageNum",required = true,defaultValue = "0") int pageNum,
			@RequestParam(name = "pageSize",required = true,defaultValue = "10") int pageSize) {
		SysDict dict = new SysDict();
		dict.setDicName(dictName);	
		return sysDictService.queryPage(dict, pageNum, pageSize);
	}
	
	
	@LogAnnotation("字典修改")
	@RequestMapping(value = "/updateDict" , method = RequestMethod.POST ,produces={"application/json"})
	public Result updateDict(@RequestBody SysDict dict) {
		return sysDictService.updateDict(dict);
	}
	
	@LogAnnotation("字典删除")
	@RequestMapping(value = "/deleteDict" , method = RequestMethod.POST ,produces={"application/json"})
	public Result deleteDict(@RequestBody String dictId) {
		return sysDictService.deleteDict(dictId,this.getUserId());
	}
	
	@LogAnnotation("字典详情")
	@RequestMapping(value = "/info/{id}" , method = RequestMethod.GET ,produces={"application/json"})
	public Result getDictInfo(@PathVariable("id")Integer id) {
		return sysDictService.getDictInfo(id);
	}
}
