package com.jee4a.oss.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.auth.common.vo.SysRoleVO;
import com.jee4a.oss.auth.model.sys.SysRole;

public interface SysRoleMapper {
	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(SysRole record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(SysRole record);

	/**
	 * 根据主键查询记录
	 */
	SysRole selectByPrimaryKey(Integer id);

	SysRole queryRoleInfo(@Param("id") Integer id);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(SysRole record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(SysRole record);

	/**
	 * 根据条件查询角色列表
	 */
	List<SysRole> querySysRoleList(@Param("record") SysRoleVO record,
			@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	List<SysRole> queryList(SysRole record);

	/**
	 * 
	 * 根据角色名称查询记录
	 * 
	 * @param roleName
	 * @return
	 * @author chl
	 * @date 2018年5月18日
	 */
	SysRole selectByRoleName(String roleName);

	SysRole queryUserRoleInfo(@Param("userId")Integer userId);
}