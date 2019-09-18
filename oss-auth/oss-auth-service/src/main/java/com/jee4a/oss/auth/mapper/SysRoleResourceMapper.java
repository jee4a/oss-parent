package com.jee4a.oss.auth.mapper;

import java.util.List;

import com.jee4a.oss.auth.model.sys.SysRoleResource;

public interface SysRoleResourceMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysRoleResource record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysRoleResource record);

    /**
     * 根据主键查询记录
     */
    SysRoleResource selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysRoleResource record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysRoleResource record);
    /**
     * 根据条件查询权限列表
     */
    List <SysRoleResource> querySysRoleResourceList(SysRoleResource sysRoleResource);
    /**
     * 根据条件查询权限
     */
    SysRoleResource querySysRoleResource(SysRoleResource sysRoleResource);
}