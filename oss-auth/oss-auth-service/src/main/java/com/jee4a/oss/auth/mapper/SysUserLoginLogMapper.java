package com.jee4a.oss.auth.mapper;

import com.jee4a.oss.auth.model.sys.SysUserLoginLog;

public interface SysUserLoginLogMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysUserLoginLog record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysUserLoginLog record);

    /**
     * 根据主键查询记录
     */
    SysUserLoginLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysUserLoginLog record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysUserLoginLog record);
}