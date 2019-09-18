package com.jee4a.oss.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jee4a.oss.admin.model.SysOperationLog;

public interface SysOperationLogMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysOperationLog record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysOperationLog record);

    /**
     * 根据主键查询记录
     */
    SysOperationLog selectByPrimaryKey(Long id);
    
    List<SysOperationLog> queryPage(@Param("record") SysOperationLog record ,@Param ("pageNum") int pageNum, @Param ("pageSize") int pageSize);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysOperationLog record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysOperationLog record);
}