package com.jee4a.oss.auth.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.common.enums.SysDeletedEnum;
import com.jee4a.oss.auth.mapper.SysDictMapper;
import com.jee4a.oss.auth.model.sys.SysDict;
import com.jee4a.oss.framework.BaseManager;

@Component
public class SysDictManager extends BaseManager{
	@Resource
	private SysDictMapper sysDictMapper;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void addDict(SysDict dict) {
		dict.setCreateTime(new Date());
		dict.setIsDeleted(SysDeletedEnum.NORMAL.getK());
		sysDictMapper.insertSelective(dict);
	}
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public PageInfo<SysDict> queryPage(SysDict dict,int pageNum,int pageSize){
		PageHelper.startPage(pageNum,pageSize);  
    	List<SysDict> list = sysDictMapper.queryPage(dict,pageNum, pageSize);
    	return new PageInfo<>(list);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void updateDict(SysDict dict) {
		dict.setUpdateTime(new Date());
		sysDictMapper.updateByPrimaryKeySelective(dict);
	}
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public SysDict getDictInfo(Integer id) {
		return sysDictMapper.selectByPrimaryKey(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public List<SysDict> selectListByType(String type) {
		return sysDictMapper.selectListByType(type) ;
	}
	
	public SysDict selectByTypeAndCode(String type,String code) {
		return sysDictMapper.selectByTypeAndCode(type,code);
	}
}
