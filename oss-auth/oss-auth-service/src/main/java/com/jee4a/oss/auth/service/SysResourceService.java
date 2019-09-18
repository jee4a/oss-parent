package com.jee4a.oss.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.common.enums.ResourceStatusEnum;
import com.jee4a.oss.auth.common.enums.ResourceTypeEnum;
import com.jee4a.oss.auth.common.vo.SysResourceVO;
import com.jee4a.oss.auth.manager.SysResourceManager;
import com.jee4a.oss.auth.manager.SysUserManager;
import com.jee4a.oss.auth.mapper.SysOrgMapper;
import com.jee4a.oss.auth.mapper.SysRoleOrgMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.mapper.SysUserRoleMapper;
import com.jee4a.oss.auth.model.sys.SysOrg;
import com.jee4a.oss.auth.model.sys.SysResource;
import com.jee4a.oss.auth.model.sys.SysRoleOrg;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.lang.StringUtils;

/**
 * 菜单资源业务接口
 * 
 * @author tpeng 2018年3月5日
 * @email 398222836@qq.com
 */
@Service
@SuppressWarnings("rawtypes")
public class SysResourceService extends BaseService {

	@Resource
	private SysUserMapper sysUserMapper;

	@Resource
	private SysResourceManager sysResourceManager;

    @Resource
    private SysRoleOrgMapper sysRoleOrgMapper;

    @Resource
    private SysOrgMapper sysOrgMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysUserManager sysUserManager;


    public Result<SysResourceVO> queryById(Integer id) {
		Result<SysResourceVO> result = new Result<>();
		try {
			if (null == id) {
				throw new BusinessException(-1000, "id不能为空");
			}
			SysResource model = sysResourceManager.selectByPrimaryKey(id);
			if (null != model) {
				SysResourceVO vo = SysResourceVO.toVO(model);
				SysResource parent = sysResourceManager.selectByPrimaryKey(model.getParentId());
				if (null != parent) {
					vo.setParentName(parent.getResourceName());
				}
				result.put(vo);
				result.setSuccess();
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
	 * @date 2018年3月13日
	 */
	private void checkParams(SysResourceVO vo) {
		if (StringUtils.isEmpty(vo.getResourceName())) {
			throw new BusinessException(-1000, "菜单名称不能为空");
		}

		if (null == vo.getParentId()) {
			throw new BusinessException(-1001, "上级菜单不能为空");
		}

		// 上级菜单类型
		int parentType = ResourceTypeEnum.CATALOG.getK();
		if (vo.getParentId() != 0) {
			SysResource parentResource = sysResourceManager.selectByPrimaryKey(vo.getParentId());
			parentType = parentResource.getResourceType();
		}

		// 目录
		if (vo.getResourceType() == ResourceTypeEnum.CATALOG.getK() && parentType != ResourceTypeEnum.CATALOG.getK()) {
			throw new BusinessException(-1002, "上级菜单只能为目录类型");
		}
		// 菜单
		if (vo.getResourceType() == ResourceTypeEnum.MEHU.getK() && parentType != ResourceTypeEnum.CATALOG.getK()) {
			throw new BusinessException(-1002, "上级菜单只能为目录类型");
		}

		// 按钮
		if (vo.getResourceType() == ResourceTypeEnum.BUTTON.getK() && parentType != ResourceTypeEnum.MEHU.getK()) {
			throw new BusinessException(-1003, "上级菜单只能为菜单类型");
		}
	}

	/**
	 * @description 菜单新增
	 * @param vo
	 * @return
	 * @date 2018年3月13日
	 */
	public Result saveReource(SysResourceVO vo, Integer operator) {
		Result result = new Result();
		try {
			checkParams(vo);
			SysResource model = SysResourceVO.toModel(vo);
			model.setCreateTime(new Date());
			model.setCreator(operator);
			sysResourceManager.insertSelective(model);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	public Result updateReource(SysResourceVO vo, Integer operator) {
		Result result = new Result();
		try {
			if (null == vo.getId()) {
				throw new BusinessException(-1000, "id error");
			}
			checkParams(vo);
			SysResource model = SysResourceVO.toModel(vo);
			model.setUpdateTime(new Date());
			model.setUpdator(operator);
			sysResourceManager.updateByPrimaryKeySelective(model);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	public Result deleteReource(Integer id, Integer operator) {
		Result result = new Result();
		try {
			if (null == id) {
				throw new BusinessException(-1000, "id error");
			}
			SysResource sysResource =new SysResource();
            sysResource.setParentId(id);
			List<SysResource> list = sysResourceManager.selectListByParentId(sysResource);
			if (!list.isEmpty()) {
				throw new BusinessException(-1002, "请先删除子菜单或按钮");
			}
			SysResource model = new SysResource();
			model.setId(id);
			model.setIsDeleted(ResourceStatusEnum.DELETED.getK());
			model.setUpdateTime(new Date());
			model.setUpdator(operator);
			sysResourceManager.updateByPrimaryKeySelective(model);
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
	 * @description 查询非按钮资源
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	public Result<List<SysResourceVO>> selectNotButtonTreeList(Integer userId) {
		Result<List<SysResourceVO>> result = new Result<>();

		try {
			List<SysResourceVO> resourceVOList = new ArrayList<>();
			// 添加顶级菜单
			SysResourceVO root = new SysResourceVO();
			root.setId(0);
			root.setResourceName("一级菜单");
			root.setName("一级菜单");
			root.setParentId(-1);
			root.setOpen(true);
			resourceVOList.add(root);
            SysUser user = sysUserMapper.selectByPrimaryKey(userId);
            SysResource record = new SysResource();
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
                record.setOrgIdList(childOrgIds);
            }
			List<SysResource> resourceList = sysResourceManager.selectNotButtonList(record);
			if (!resourceList.isEmpty()) {
				SysResourceVO vo = null;
				SysResource sysResource = null;
				for (SysResource model : resourceList) {
					vo = SysResourceVO.toVO(model);
					vo.setName(model.getResourceName());
					sysResource = sysResourceManager.selectByPrimaryKey(model.getParentId());
					if (null != sysResource) {
						vo.setParentName(sysResource.getResourceName());
					}
					resourceVOList.add(vo);
				}
			}
			result.put(resourceVOList);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	/**
	 * @description 查询所有菜单
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	public Result<List<SysResourceVO>> queryResourceList(Integer userId) {
		Result<List<SysResourceVO>> result = new Result<>();
		try {
			List<SysResourceVO> resourceVOList = new ArrayList<>();
            SysResource record = new SysResource();

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
                record.setOrgIdList(childOrgIds);
            }
			List<SysResource> resourceList = sysResourceManager.selectList(record);
			if (!resourceList.isEmpty()) {
				SysResourceVO vo = null;
				SysResource sysResource = null;
				for (SysResource model : resourceList) {
					vo = SysResourceVO.toVO(model);
					vo.setName(model.getResourceName());
					sysResource = sysResourceManager.selectByPrimaryKey(model.getParentId());
					if (null != sysResource) {
						vo.setParentName(sysResource.getResourceName());
						
					}
					vo.setShow(model.getIsShow());
					resourceVOList.add(vo);
				}
			}
			result.put(resourceVOList);
			result.setSuccess();
			result.print();
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
	/**
	 * 
	 */
	/**
	 * @description 获取用户具备的 目录，菜单 集合
	 * @author 
	 * @date 2018年3月13日
	 */
	public Result<List<SysResourceVO>> queryUserResourceList(Integer userId) {
		Result<List<SysResourceVO>> result = new Result<>();
		try {
			List<SysResourceVO> resourceList = null;
			// 系统管理员，拥有最高权限
			if (userId == BaseConstant.SUPER_ADMIN) {
				resourceList = getAllMenuList(null);
			} else {
				// 用户菜单列表
				List<Integer> menuIdList = sysUserMapper.queryAllResourceId(userId);
				resourceList = getAllMenuList(menuIdList);
			}
			result.put(resourceList);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	/**
	 * @description 获取所有菜单列表
	 * @param @param
	 *            menuIdList
	 * @param @return
	 * @return List<SysResourceVO> 返回类型
	 * @author 
	 * @date 2018年3月13日
	 */
	private List<SysResourceVO> getAllMenuList(List<Integer> menuIdList) {
		// 查询根菜单列表
		List<SysResourceVO> menuList = selectListByParentId(0, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 查询根菜单
	 * 
	 * @description
	 * @author 
	 * @date 2018年3月12日
	 */
	public List<SysResourceVO> selectListByParentId(Integer parentId, List<Integer> menuIdList) {
        SysResource sysResource = new SysResource();
        sysResource.setParentId(parentId);
		List<SysResource> menuList = sysResourceManager.selectListByParentId(sysResource);
		List<SysResourceVO> menuVOList = new ArrayList<>();
		List<SysResourceVO> userMenuList = new ArrayList<>();
		if (menuIdList == null) {
			if (!menuList.isEmpty()) {
				SysResourceVO vo = null;
				for (SysResource model : menuList) {
					vo = SysResourceVO.toVO(model);
					menuVOList.add(vo);
				}
				return menuVOList;
			}
		} else {
			SysResourceVO vo = null;
			for (SysResource menu : menuList) {
				if (menuIdList.contains(menu.getId())) {
					vo = SysResourceVO.toVO(menu);
					userMenuList.add(vo);
				}
			}
		}
		return userMenuList;
	}

	/**
	 * @description 递归
	 * @param menuList
	 * @param menuIdList
	 * @return
	 * @author 
	 * @date 2018年3月13日
	 */
	private List<SysResourceVO> getMenuTreeList(List<SysResourceVO> menuList, List<Integer> menuIdList) {
		List<SysResourceVO> subMenuList = new ArrayList<>();

		for (SysResourceVO vo : menuList) {
			// 目录
			if (vo.getResourceType().byteValue() == ResourceTypeEnum.CATALOG.getK()) {
				vo.setList(getMenuTreeList(selectListByParentId(vo.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(vo);
		}

		return subMenuList;
	}

}
