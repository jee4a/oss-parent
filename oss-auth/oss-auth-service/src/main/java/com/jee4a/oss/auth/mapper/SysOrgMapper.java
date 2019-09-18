package com.jee4a.oss.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.jee4a.oss.auth.model.sys.SysOrg;
@Component
public interface SysOrgMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SysOrg record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SysOrg record);

    /**
     * 根据主键查询记录
     */
    SysOrg selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SysOrg record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SysOrg record);
    
    /**
     * 条件查询
     */
    List<SysOrg> queryList(SysOrg record);
    /**
     * 根据父节点Id查询
     * @param parentId
     * @return
     */
    List<SysOrg> queryByParentId(@Param("parentId") Integer parentId);
}