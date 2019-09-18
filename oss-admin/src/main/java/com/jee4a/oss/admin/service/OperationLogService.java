package com.jee4a.oss.admin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee4a.oss.admin.mapper.SysOperationLogMapper;
import com.jee4a.oss.admin.model.SysOperationLog;
import com.jee4a.oss.framework.BaseManager;

@Service
public class OperationLogService extends  BaseManager{
	
	@Resource
	private SysOperationLogMapper sysOperationLogMapper ;
     
    /**
     * 保存属性不为空的记录
     */
    public void  insertSelective(SysOperationLog record) {
    	sysOperationLogMapper.insertSelective(record) ;
    }

    /**
     * 根据主键查询记录
     */
    public SysOperationLog selectByPrimaryKey(Long id) {
    	return sysOperationLogMapper.selectByPrimaryKey(id) ;
    }

    /**
     * 根据主键更新属性不为空的记录
     */
    public void  updateByPrimaryKeySelective(SysOperationLog record) {
    	sysOperationLogMapper.updateByPrimaryKeySelective(record) ;
    }
 
    
    public PageInfo<SysOperationLog> queryPage(SysOperationLog record,int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum,pageSize);  
    	List<SysOperationLog> list = sysOperationLogMapper.queryPage(record,pageNum, pageSize) ;
    	return  new PageInfo<>(list) ;
    }

}