package com.jee4a.oss.auth.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.auth.common.vo.SysUserVO;
import com.jee4a.oss.auth.model.sys.SysUser;

public interface SysUserMapper {
	
	List<String> queryAllResourceUrl(Integer userId);
	
	/**
	 * 查询用户的所有权限
	 * 
	 * @param userId
	 *            用户ID
	 */
	List<String> queryAllPerms(Integer userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Integer> queryAllResourceId(Integer userId);

	/**
	 * 根据主键查询记录
	 */
	SysUser selectByUserName(@Param("userName") String userName);

	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	Integer insert(SysUser record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(SysUser record);

	/**
	 * 根据主键查询记录
	 */
	SysUser selectByPrimaryKey(Integer id);

	SysUserVO selectUserDeailts(@Param("userId") Integer userId);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(SysUser record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(SysUser record);

	List<SysUserVO> queryPage(SysUserVO sysUserVO);

	List<SysUser> selectByPrimaryKeySelective(SysUser record);

	SysUser queryByName(@Param("userName") String userName);

	List<SysUser> findByMap(Map<String, Object> map);

	/**
	 * 
	 * 根据手机号查询
	 * 
	 * @param mobile
	 * @return
	 * @author chl
	 * @date 2018年5月17日
	 */
	SysUser selectByMobile(@Param("mobile") String mobile);
}