package com.jee4a.oss.auth.mapper;

import java.util.List;

import com.jee4a.oss.auth.model.sys.SysResource;

public interface SysResourceMapper {
	
	List<SysResource>   selectNotButtonList (SysResource record) ;
	
	List<SysResource>  selectListByParentId(SysResource record) ;
	
	List<SysResource> selectList(SysResource record) ;
	
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysResource record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysResource record);

    /**
     * 根据主键查询记录
     */
    SysResource selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysResource record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysResource record);
}