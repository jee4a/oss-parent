package com.jee4a.oss.auth.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.jee4a.oss.auth.common.vo.SysPositionVO;
import com.jee4a.oss.auth.model.sys.SysPosition;
import com.jee4a.oss.auth.service.SysPositionService;
import com.jee4a.oss.framework.BaseController;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.annotation.LogAnnotation;

@RestController
@RequestMapping("/sys/position")
@SuppressWarnings("rawtypes")
public class SysPositionController extends BaseController {

	@Resource
	private SysPositionService sysPositionService;

	@LogAnnotation("职位添加")
	@RequestMapping(value = "/addPosition", method = RequestMethod.POST, produces = { "application/json" })
	public Result addPosition(@RequestBody SysPosition position) {
		position.setCreator(getUserId());
		return sysPositionService.addPosition(position);
	}

	@LogAnnotation("职位列表")
	@RequestMapping(value = "/queryPage", method = RequestMethod.GET, produces = { "application/json" })
	public Result queryPage(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "pageNum", required = true, defaultValue = "0") int pageNum,
			@RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize) {

		SysPositionVO position = new SysPositionVO();
		position.setPositionName(name);
		if (StringUtil.isNotEmpty(state)) {
			position.setState(Byte.valueOf(state));
		}
		return sysPositionService.queryPage(position, pageNum, pageSize);
	}

	@LogAnnotation("职位修改")
	@RequestMapping(value = "/updatePosition", method = RequestMethod.POST, produces = { "application/json" })
	public Result updatePosition(Integer state,Integer id) {
		SysPosition position = new SysPosition();
		position.setState(Byte.valueOf(state+""));
		position.setId(id);
		return sysPositionService.updatePosition(position);
	}

	@LogAnnotation("职位查询")
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public Result info(@PathVariable("id") Integer id) {
		return sysPositionService.getPositionById(id);
	}
}
