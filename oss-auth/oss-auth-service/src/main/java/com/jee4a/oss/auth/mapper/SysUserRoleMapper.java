package com.jee4a.oss.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.auth.model.sys.SysUserRole;

public interface SysUserRoleMapper {
	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(@Param("id") Integer id,
			@Param("userId") Integer userId, @Param("roleId") Integer roleId);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(SysUserRole record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(SysUserRole record);

	/**
	 * 根据主键查询记录
	 */
	SysUserRole selectByPrimaryKey(Integer id);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(SysUserRole record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(SysUserRole record);

	SysUserRole selectByUserIdAndRoleId(Integer userId, Integer roleId);

	Integer queryUserRoleId(@Param("userId") Integer userId);

	/**
	 * 根据角色id查询用户角色表数据
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysUserRole> selectByRoleId(Integer roleId);
	
	/**
	 * 根据角色id查询用户所有角色名称
	 * 
	 * @param userId
	 * @return
	 */
	String querUserListName(Integer id);


    List<SysUserRole> getStaffIdByRoleList(@Param("roleIdList") List<Integer> roleIdList);
}