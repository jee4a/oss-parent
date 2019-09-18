package com.jee4a.oss.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.auth.common.vo.SysPositionVO;
import com.jee4a.oss.auth.model.sys.SysPosition;

public interface SysPositionMapper {
	/**
	 * 根据主键删除记录
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 保存记录,不管记录里面的属性是否为空
	 */
	int insert(SysPosition record);

	/**
	 * 保存属性不为空的记录
	 */
	int insertSelective(SysPosition record);

	/**
	 * 根据主键查询记录
	 */
	SysPosition selectByPrimaryKey(Integer id);

	/**
	 * 根据主键更新属性不为空的记录
	 */
	int updateByPrimaryKeySelective(SysPosition record);

	/**
	 * 根据主键更新记录
	 */
	int updateByPrimaryKey(SysPosition record);

	/**
	 * 分页查询
	 */
	List<SysPositionVO> queryPage(@Param("position") SysPosition position,
			@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	List<SysPosition> queryList(SysPosition position);

	/**
	 * 
	 * 根据职位名称查询记录
	 * 
	 * @param positionName
	 * @return
	 * @author chl
	 * @date 2018年5月18日
	 */
	SysPosition selectByPositionName(String positionName);

}