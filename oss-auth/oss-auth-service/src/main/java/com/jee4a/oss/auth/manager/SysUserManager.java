package com.jee4a.oss.auth.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.common.enums.SysOrgStateEnum;
import com.jee4a.oss.auth.common.enums.SysUserStateEnum;
import com.jee4a.oss.auth.common.vo.SysUserVO;
import com.jee4a.oss.auth.mapper.SysOrgMapper;
import com.jee4a.oss.auth.mapper.SysRoleOrgMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.mapper.SysUserRoleMapper;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.auth.model.sys.SysUserRole;
import com.jee4a.oss.framework.BaseManager;
import com.jee4a.oss.framework.exceptions.BusinessException;
@Component
public class SysUserManager extends BaseManager{
	
	
	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysOrgMapper sysOrgMapper;
	
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	@Resource
    private SysRoleOrgMapper sysRoleOrgMapper;
	
	public PageInfo<SysUserVO> queryPage(SysUserVO sysUserVO,int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum,pageSize);  
    	List<SysUserVO> list = sysUserMapper.queryPage(sysUserVO) ;
    	for (SysUserVO user : list) {
    		String roleNames=sysUserRoleMapper.querUserListName(user.getId());
    		user.setRoleName(roleNames);
		}
    	return new PageInfo<>(list)  ;
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void insertUser(SysUserVO userVo) {
		SysUser user = SysUserVO.toModel(userVo);
		user.setCreateTime(new Date());
		user.setIsDeleted(SysUserStateEnum.NORMAL.getK());
		sysUserMapper.insert(user);
		
		userVo.setRoleIdList(Arrays.asList(userVo.getRoleId()));
		if(!CollectionUtils.isEmpty(userVo.getRoleIdList())) {
			this.inserUserAndRole(userVo.getRoleIdList(), user.getId());
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public SysUser selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
	
	public SysUserVO getUserInfo(Integer userId) {
		return sysUserMapper.selectUserDeailts(userId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void updateUserInfo(SysUserVO userVO) {
		SysUser user = SysUserVO.toModel(userVO);
		logger.info("修改后的部门职位position_id="+user.getPositionId());
		Integer userId = userVO.getId();
		if(userId == null) {
			return;
		}
		user.setUpdateTime(new Date());
		sysUserMapper.updateByPrimaryKeySelective(user);

		Integer roleIdOld= sysUserRoleMapper.queryUserRoleId(user.getId());
		if(roleIdOld != null){
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(userVO.getRoleId());
            userRole.setUpdateTime(new Date());
            userRole.setUpdator(userVO.getUpdator());
            sysUserRoleMapper.updateByPrimaryKeySelective(userRole);
            return;
        }
        inserUserAndRole(userVO.getRoleId(),userId);
	}
	

	public void inserUserAndRole(Integer roleId,Integer userId) {
		if(roleId == null) {
			throw new BusinessException(-3001, "修改失败，角色列表为空");
		}
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setCreateTime(new Date());
        userRole.setRoleId(roleId);
        userRole.setIsDeleted(SysOrgStateEnum.NORMAL.getK());
        sysUserRoleMapper.insert(userRole);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void inserUserAndRole(List<Integer> roleList,Integer userId) {
		if(CollectionUtils.isEmpty(roleList)) {
			throw new BusinessException(-3001, "修改失败，角色列表为空");
		}
		for (Integer roleId : roleList) {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(userId);
			userRole.setCreateTime(new Date());
			userRole.setRoleId(roleId);
			userRole.setIsDeleted(SysOrgStateEnum.NORMAL.getK());
			sysUserRoleMapper.insert(userRole);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void deleteUser(List<String> userIdList,Integer updateId) {
		//删除用户
		for (String userId : userIdList) {
			SysUser user = new SysUser();
			user.setUpdator(updateId);
			user.setId(Integer.valueOf(userId));
			user.setIsDeleted(SysOrgStateEnum.DELETED.getK());
			user.setUpdateTime(new Date());
			sysUserMapper.updateByPrimaryKeySelective(user);
			
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(Integer.valueOf(userId));
			userRole.setIsDeleted(SysOrgStateEnum.DELETED.getK());
			userRole.setUpdateTime(new Date());
			userRole.setUpdator(user.getUpdator());
			sysUserRoleMapper.updateByPrimaryKeySelective(userRole);
		}
	}

	public List<Integer> getStaffIdList(List<Integer> childOrgIds){
        List<Integer> staffIdList = new ArrayList<>();
        //部门下的所有角色
        List<Integer> roleIdList = sysRoleOrgMapper.getRoleByOrgIdList(childOrgIds);
        if (CollectionUtils.isEmpty(roleIdList)){
            logger.info("getStaffIdList获取角色信息 null");
            return  staffIdList;
        }
        logger.info("getStaffIdList获取角色信息 roleIdList = "+roleIdList);
      /*  List<Integer> roleList = new ArrayList<>();
        for (SysRoleOrg roleOrg : roleOrgList) {
            logger.info("角色id= " + roleOrg.getRoleId());
            roleList.add(roleOrg.getRoleId());
        }*/
        //角色对应的所有用户
        List<SysUserRole> userRoleList = sysUserRoleMapper.getStaffIdByRoleList(roleIdList);
        if (CollectionUtils.isEmpty(userRoleList)){
            logger.info("getStaffIdList获取用户信息 null");
            return  staffIdList;
        }
        logger.info("getStaffIdList获取用户信息 userRoleList = "+userRoleList);
       /* userRoleList.stream().forEach(sysUserRole -> {
            staffIdList.add(sysUserRole.getUserId());
        });*/
        for (SysUserRole sysUserRole:userRoleList) {
            staffIdList.add(sysUserRole.getUserId());
        }
        return  staffIdList;
    }
}
