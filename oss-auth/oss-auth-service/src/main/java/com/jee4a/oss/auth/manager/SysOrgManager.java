package com.jee4a.oss.auth.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.common.enums.RoleStateEnum;
import com.jee4a.oss.auth.common.enums.SysOrgStateEnum;
import com.jee4a.oss.auth.mapper.SysOrgMapper;
import com.jee4a.oss.auth.mapper.SysRoleOrgMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.mapper.SysUserRoleMapper;
import com.jee4a.oss.auth.model.sys.SysOrg;
import com.jee4a.oss.auth.model.sys.SysRoleOrg;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.auth.model.sys.SysUserRole;
import com.jee4a.oss.framework.BaseManager;
@Component
public class SysOrgManager extends BaseManager{
	@Resource
	private SysOrgMapper sysOrgMapper;
	
	@Resource
	private SysRoleOrgMapper sysRoleOrgMapper;
	
	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	
	
	public void insert(SysOrg org,Integer craterId) {
		org.setCreateTime(new Date());
		org.setIsDeleted(SysOrgStateEnum.NORMAL.getK());
		org.setCreator(craterId);
		sysOrgMapper.insert(org);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void saveUser(SysUser sysUser){
		sysUserMapper.updateByPrimaryKeySelective(sysUser);
		SysUserRole userRole = new SysUserRole();
		userRole.setUserId(sysUser.getId());
		userRole.setIsDeleted(SysOrgStateEnum.DELETED.getK());
		userRole.setUpdateTime(new Date());
		userRole.setUpdator(sysUser.getUpdator());
		sysUserRoleMapper.updateByPrimaryKeySelective(userRole);
	}
	
	public List<SysUser> getUserByMap(Map<String,Object> map){
		return sysUserMapper.findByMap(map);
	}
	
	public SysUser getUser(int id){
		return sysUserMapper.selectByPrimaryKey(id);
	}
	
	public void update(SysOrg org) {
		sysOrgMapper.updateByPrimaryKeySelective(org);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void delete(SysOrg sysOrg) {
		sysOrg.setParentId(null);
		sysOrg.setIsDeleted(SysOrgStateEnum.DELETED.getK());
		sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
		
		
		SysRoleOrg roleOrg = new SysRoleOrg();
		roleOrg.setOrgId(sysOrg.getId());
		roleOrg.setIsDeleted(RoleStateEnum.CLOSE.getKey());
		sysRoleOrgMapper.updateByPrimaryKeySelective(roleOrg);
	}


	public List<SysOrg> queryList(SysOrg sysOrg) {
		return sysOrgMapper.queryList(sysOrg);
	}


}
