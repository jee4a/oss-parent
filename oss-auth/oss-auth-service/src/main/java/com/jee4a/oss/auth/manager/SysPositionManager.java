package com.jee4a.oss.auth.manager;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee4a.oss.auth.common.enums.SysDeletedEnum;
import com.jee4a.oss.auth.common.enums.SysUserStateEnum;
import com.jee4a.oss.auth.common.vo.SysPositionVO;
import com.jee4a.oss.auth.mapper.SysPositionMapper;
import com.jee4a.oss.auth.model.sys.SysPosition;
import com.jee4a.oss.framework.BaseManager;
@Component
public class SysPositionManager extends BaseManager{
	
	@Resource
	private SysPositionMapper sysPositionMapper;
	
	public void addPosition(SysPosition position) {
		position.setCreateTime(new Date());
		position.setIsDeleted(SysDeletedEnum.NORMAL.getK());
		position.setState(SysUserStateEnum.NORMAL.getK());
		sysPositionMapper.insertSelective(position);
	}
	
	public PageInfo<SysPositionVO> queryPage(SysPositionVO position,int pageNum,int pageSize){
		PageHelper.startPage(pageNum,pageSize);  
    	List<SysPositionVO> list = sysPositionMapper.queryPage(position,pageNum, pageSize);
    	return new PageInfo<>(list);
	}
	
	public void updatePosition(SysPosition position) {
		sysPositionMapper.updateByPrimaryKeySelective(position);
	}
	
	public List<SysPosition> queryList(SysPosition position) {
		return sysPositionMapper.queryList(position);
	}
}
