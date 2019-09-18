package com.jee4a.oss.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jee4a.oss.auth.common.enums.SysRoleTypeEnum;
import com.jee4a.oss.auth.common.vo.SysRoleVO;
import com.jee4a.oss.auth.manager.SysRoleManager;
import com.jee4a.oss.auth.mapper.SysOrgMapper;
import com.jee4a.oss.auth.mapper.SysRoleMapper;
import com.jee4a.oss.auth.mapper.SysRoleOrgMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.mapper.SysUserRoleMapper;
import com.jee4a.oss.auth.model.sys.SysOrg;
import com.jee4a.oss.auth.model.sys.SysRole;
import com.jee4a.oss.auth.model.sys.SysRoleOrg;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.auth.model.sys.SysUserRole;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.lang.StringUtils;

/**
 * 角色业务接口
 * 
 * @email 
 */
@Service
public class SysRoleService extends BaseService {

	@Resource
	private SysRoleManager sysRoleManager;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Resource
	private SysRoleMapper sysRoleMapper;

	@Resource
    private SysRoleOrgMapper sysRoleOrgMapper;

	@Resource
    private SysUserMapper sysUserMapper;

	@Resource
    private SysOrgMapper sysOrgMapper;

	/**
	 * @description 获取角色列表
	 * @param vo
	 * @author 
	 * @date 2018年3月16日
	 */
	public Result<List<SysRoleVO>> queryRoleListByPage(SysRoleVO sysRoleVO,
			int pageNum, int pageSize,Integer userId) {
		Result<List<SysRoleVO>> result = new Result<>();
		try {

			if (StringUtils.isNotBlank(sysRoleVO
					.getQueryConditions())) {
				if (SysRoleTypeEnum.ROLEID.getK().equals(
						sysRoleVO.getQueryConditions())) {
					sysRoleVO.setId(Integer.parseInt(sysRoleVO.getName()));
				} else if (SysRoleTypeEnum.ROLENAME.getK().equals(
						sysRoleVO.getQueryConditions())) {
					sysRoleVO.setRoleName(sysRoleVO.getName());
				} else if (SysRoleTypeEnum.CREATENAME.getK().equals(
						sysRoleVO.getQueryConditions())) {
					sysRoleVO.setCreateName(sysRoleVO.getName());
				} else if (SysRoleTypeEnum.UPDATENAME.getK().equals(
						sysRoleVO.getQueryConditions())) {
					sysRoleVO.setUpdateName(sysRoleVO.getName());
				}
			}
            SysUser user = sysUserMapper.selectByPrimaryKey(userId);
            //如果不是admin账号，只能显示所在部门及以下部门信息
            if(!"admin".equals(user.getUserName())){
                Integer roleId = sysUserRoleMapper.queryUserRoleId(userId);

                SysRoleOrg org = new SysRoleOrg();
                org.setRoleId(roleId);
                SysRoleOrg roleOrg = sysRoleOrgMapper.querySysRoleOrg(org);
                Integer orgId = roleOrg != null ? roleOrg.getOrgId() : null;

                List<Integer> childOrgIds =new ArrayList<Integer>();
                List<SysOrg> orgList= sysOrgMapper.queryList(null);
                childOrgIds = treeOrgsList(orgList, orgId,childOrgIds);
                sysRoleVO.setDeptIdList(childOrgIds);
            }
			PageInfo<SysRole> pageInfo = sysRoleManager.queryRoleListByPage(
					sysRoleVO, pageNum, pageSize);
			result.put("pageInfo", pageInfo);
			result.setSuccess();
			result.print();
		} catch (Exception e) {
			result.setDefaultError();
			logger.error("error : {}", e.getMessage());
		}
		return result;
	}

	/**
	 * @description 新增角色
	 * @param vo
	 * @author 
	 * @date 2018年3月16日
	 */
	public Result addSysRole(SysRole sysRole, Integer userId) {
		Result result = new Result();
		try {
			checkParams(sysRole);
			sysRoleManager.addSysRole(sysRole, userId);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		result.print();
		return result;
	}

	/**
	 * @description 查询角色
	 * @param vo
	 * @author 
	 * @date 2018年3月16日
	 */
	public Result queryRole(Integer roleId) {
		Result result = new Result();
		try {
			SysRole sysRole = sysRoleManager.queryRole(roleId);
			result.put("role", sysRole);
			result.setSuccess();
			result.print();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	/**
	 * @description 修改角色
	 * @param vo
	 * @author 
	 * @date 2018年3月16日
	 */
	public Result updateSysRole(SysRole sysRole, Integer userId) {
		Result result = new Result();
		try {
			checkParams(sysRole);
			sysRoleManager.updateSysRole(sysRole, userId);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	/**
	 * @description 启动角色
	 * @param vo
	 * @author 
	 * @date 2018年3月16日
	 */
	public Result open(Integer[] roleIds) {
		Result result = new Result();
		try {
			sysRoleManager.open(roleIds);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	/**
	 * @description 关闭角色
	 * @param vo
	 * @author 
	 * @date 2018年3月16日
	 */
	public Result close(Integer[] roleIds) {
		Result result = new Result();
		List<Integer> roleIdList = new ArrayList<Integer>(0);
		try {

			for (Integer roleId : roleIds) {
				List<SysUserRole> sysUserRoleList = sysUserRoleMapper
						.selectByRoleId(roleId);
				if (sysUserRoleList != null && sysUserRoleList.size() > 0) {
				} else {
					roleIdList.add(roleId);
				}
			}
			if (roleIdList.size() > 0) {
				Integer[] roleIdss = new Integer[roleIdList.size()];
				roleIdList.toArray(roleIdss);
				sysRoleManager.close(roleIdss);
				result.setSuccess();
			} else {
				throw new BusinessException(-1000, "角色正在使用中，不可禁用！");
			}

		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	/**
	 * @description 入参检查
	 * @param vo
	 * @date 2018年3月16日
	 */
	private void checkParams(SysRole vo) {
		if (StringUtils.isEmpty(vo.getRoleName())) {
			throw new BusinessException(-1000, "角色名称不能为空");
		}
        // 查询角色名是否已存在
        SysRole role = sysRoleMapper.selectByRoleName(vo.getRoleName());
        if (role != null && role.getId() != vo.getId()) {
            throw new BusinessException(-1000, "角色名称已存在");
        }
        if (vo.getOrgId() == null){
            throw new BusinessException(-1000, "必须选中角色所在部门！");
        }
        /*
		 * if (null == vo.getOrgId()) { throw new BusinessException(-1001,
		 * "所属部门不能为空"); } if
		 * (StringUtils.isEmpty(vo.getOrgName())) {
		 * throw new BusinessException(-1000, "所属部门不能为空"); }
		 */

	}

	public Result queryList(SysRole role,Integer userId) {
		Result result = new Result();
		try {
            SysUser user=sysUserMapper.selectByPrimaryKey(userId);
            List<Integer> childOrgIds =new ArrayList<Integer>();
            if(!"admin".equals(user.getUserName())){
                Integer roleId = sysUserRoleMapper.queryUserRoleId(userId);

                SysRoleOrg org = new SysRoleOrg();
                org.setRoleId(roleId);
                SysRoleOrg roleOrg = sysRoleOrgMapper.querySysRoleOrg(org);
                Integer orgId = roleOrg != null ? roleOrg.getOrgId() : null;

                List<SysOrg> orgList= sysOrgMapper.queryList(null);
                childOrgIds = treeOrgsList(orgList, orgId,childOrgIds);
                role.setDeptIdList(childOrgIds);
            }
			result.put(sysRoleManager.queryList(role));
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

    public  List<Integer> treeOrgsList( List<SysOrg> orgs, int orgId ,List<Integer>orgIds){
        for(SysOrg org: orgs){
            //遍历出父id等于参数的id，add进子节点集合
            if(Integer.valueOf(org.getParentId())==orgId){
                //递归遍历下一级
                treeOrgsList(orgs,org.getId(),orgIds);
                orgIds.add(org.getId());
            }
        }
        return orgIds;
    }
	public Result queryRoleInfo(Integer userId) {
		Result result = new Result();
		try {
			result.put(sysRoleManager.queryUserRoleInfo(userId));
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
}
