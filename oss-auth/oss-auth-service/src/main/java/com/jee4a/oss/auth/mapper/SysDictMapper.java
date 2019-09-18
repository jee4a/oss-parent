package com.jee4a.oss.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.auth.model.sys.SysDict;

public interface SysDictMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysDict record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysDict record);

    /**
     * 根据主键查询记录
     */
    SysDict selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysDict record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysDict record);
    
    /**
     * 分页查询
     */
    List<SysDict> queryPage(@Param("dict")SysDict record,@Param("pageNum")int pageNum,@Param("pageSize")int pageSize);
    
    List<SysDict>  selectListByType(String type) ;
    
    SysDict selectByTypeAndCode(@Param("type")String type,@Param("code")String code);
    
}