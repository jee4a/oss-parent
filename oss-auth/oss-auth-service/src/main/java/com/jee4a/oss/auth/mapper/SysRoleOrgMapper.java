package com.jee4a.oss.auth.mapper;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.auth.model.sys.SysRoleOrg;

import java.util.List;

public interface SysRoleOrgMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysRoleOrg record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysRoleOrg record);

    /**
     * 根据主键查询记录
     */
    SysRoleOrg selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysRoleOrg record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysRoleOrg record);
    /**
     * 根据条件查询权限
     */
    SysRoleOrg querySysRoleOrg(SysRoleOrg sysRoleOrg);

    List<Integer> getRoleByOrgIdList(@Param("orgIdList") List<Integer> orgIdList);
}