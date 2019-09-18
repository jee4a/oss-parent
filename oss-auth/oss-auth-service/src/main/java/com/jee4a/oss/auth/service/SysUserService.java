package com.jee4a.oss.auth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.common.enums.SysDeletedEnum;
import com.jee4a.oss.auth.common.enums.SysUserStateEnum;
import com.jee4a.oss.auth.common.vo.SysUserVO;
import com.jee4a.oss.auth.manager.SysUserManager;
import com.jee4a.oss.auth.mapper.SysOrgMapper;
import com.jee4a.oss.auth.mapper.SysResourceMapper;
import com.jee4a.oss.auth.mapper.SysRoleMapper;
import com.jee4a.oss.auth.mapper.SysRoleOrgMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.mapper.SysUserRoleMapper;
import com.jee4a.oss.auth.model.CacheKeys;
import com.jee4a.oss.auth.model.sys.SysOrg;
import com.jee4a.oss.auth.model.sys.SysResource;
import com.jee4a.oss.auth.model.sys.SysRoleOrg;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.io.cache.redis.RedisUtils;
import com.jee4a.oss.framework.lang.JsonUtils;
import com.jee4a.oss.framework.lang.PasswordUtils;
import com.jee4a.oss.framework.lang.StringUtils;


/**
 * <p>
 * <p>业务逻辑层</p> 
 * </p>
 * 业务操作成功，设置 result.setSuccess();
 */
@Service
@SuppressWarnings("rawtypes")
public class SysUserService extends BaseService {

	@Resource
	private SysUserMapper sysUserMapper ; 
	
	@Resource
	private SysUserManager sysUserManager ; 

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Resource
	private SysOrgMapper sysOrgMapper;
	
	@Resource
	private SysRoleMapper sysRoleMapper ;

    @Resource
    private SysRoleOrgMapper sysRoleOrgMapper;
    
    @Resource
    private SysResourceMapper sysResourceMapper;
    
	@Autowired
	protected RedisUtils redis;
	
	public Result password(String password, String newPassword,String trueNewPassword, Integer userId) {
		Result result = new Result();
		try {

			if (StringUtils.isEmpty(password)) {
				result.setError(-50001, "请输入原密码");
				return result;
			}
			if (StringUtils.isEmpty(newPassword)) {
				result.setError(-50002, "请输入新密码");
				return result;
			}
			if(StringUtils.isEmpty(trueNewPassword)) {
				result.setError(-50002, "确认密码不能为空");
				return result;
			}
			if(!newPassword.equals(trueNewPassword)) {
				result.setError(-50002, "两次输入密码不一致");
				return result;
			}
			SysUser user = sysUserMapper.selectByPrimaryKey(userId);

			// 原密码
			password = PasswordUtils.getPassword(password, user.getSalt()) ; 
			// 新密码
			newPassword =PasswordUtils.getPassword(newPassword, user.getSalt()) ;  

			if (!password.equals(user.getUserPwd())) {
				result.setError(-60003, "原密码错误");
				return result;
			}

			SysUser record = new SysUser();
			record.setId(user.getId());
			record.setUserPwd(newPassword);
			record.setLastUpdatePwdTime(new Date());
			sysUserMapper.updateByPrimaryKeySelective(record);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}

		return result;
	}
	
	public Result updatePassword(String userName,String password, String newPassword,String trueNewPassword) {
		Result result = new Result();
		try {

			if (StringUtils.isEmpty(password)) {
				result.setError(-50001, "请输入原密码");
				return result;
			}
			if (StringUtils.isEmpty(newPassword)) {
				result.setError(-50002, "请输入新密码");
				return result;
			}
			if(StringUtils.isEmpty(trueNewPassword)) {
				result.setError(-50002, "确认密码不能为空");
				return result;
			}
			if(!newPassword.equals(trueNewPassword)) {
				result.setError(-50002, "两次输入密码不一致");
				return result;
			}
			SysUser user = sysUserMapper.selectByUserName(userName) ;
			// 原密码
			password = PasswordUtils.getPassword(password, user.getSalt()) ; 
			// 新密码
			newPassword =PasswordUtils.getPassword(newPassword, user.getSalt()) ;  

			if (!password.equals(user.getUserPwd())) {
				result.setError(-60003, "原密码错误");
				return result;
			}
			SysUser record = new SysUser();
			record.setId(user.getId());
			record.setUserPwd(newPassword);
			record.setLastUpdatePwdTime(new Date());
			sysUserMapper.updateByPrimaryKeySelective(record);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}

		return result;
	}
	
	/**
	 * @Title:  SysUserService.java   
	 * @param user
	 * @return  Result  返回结果封装类
	 * @Description:添加用户   
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月12日 下午4:26:09   
	 * @version V1.0 
	 */
	public Result insertUser(SysUserVO user,Integer createrId) {
		Result result = new Result();
		//添加用户
		try {
			checkNoEmptyFields(user,user.getUserPwd());
			SysUser oldUser = sysUserMapper.queryByName(user.getUserName());
			if(oldUser != null && SysUserStateEnum.NORMAL.getK()==oldUser.getState()
					&&SysDeletedEnum.NORMAL.getK() == oldUser.getIsDeleted()) {
				result.setError(-20002, "用户名被占用");
				return result;
			}
			if(oldUser != null && SysUserStateEnum.DISABLED.getK()==oldUser.getState()
					&&SysDeletedEnum.NORMAL.getK() == oldUser.getIsDeleted()) {
				result.setError(-20003, "已有该用户名被禁用，请联系管理员");
				return result;
			}
			
			Map<String,Object>map =new HashMap<String,Object>();
			map.put("mobile", user.getMobile());
			List<SysUser> list=sysUserMapper.findByMap(map);
			if(list != null && list.size() > 0){
				result.setError(-20004, "手机号已被占用");
				return result;
			}
			if(user.getRoleId() == null) {
				new BusinessException(-20005,"角色不能为空");
			}

			user.setCreator(createrId);
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setSalt(salt);
			user.setUserPwd(PasswordUtils.getPassword(user.getUserPwd(), user.getSalt()));
			
			if(oldUser !=null && SysDeletedEnum.DELETED.getK() == oldUser.getIsDeleted()) {
				user.setIsDeleted(SysDeletedEnum.NORMAL.getK());
				user.setId(oldUser.getId());
				sysUserManager.updateUserInfo(user);
				result.setSuccess();
				return result;
			}
			user.setLastUpdatePwdTime(new Date());
			user.setState(SysUserStateEnum.NORMAL.getK());
			sysUserManager.insertUser(user);
			result.setSuccess();
		} catch(BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	/**
	 * 
	 * @param user    
	 * @Description:校验必传参数 
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月16日 上午11:23:44   
	 * @version V1.0 
	 */
	public void checkNoEmptyFields(SysUserVO user) {
		//用户名
		if(StringUtils.isEmpty(user.getUserName())) {
			throw new BusinessException(-2005, "用户名不能为空");
		}
		
		
		if(user.getOrgId() == null) {
			throw new BusinessException(-2006, "所属部门不能为空");
		}
		//手机号
		if(StringUtils.isEmpty(user.getMobile())) {
			throw new BusinessException(-2009, "手机号不能为空");
		}
		
		if(!StringUtils.isMobile(user.getMobile())){
			throw new BusinessException(-2011, "手机号格式不正确");
		}
		if(user.getPositionId() == null){
			throw new BusinessException(-2012, "职位不能为空");
		}
		//角色
		Integer roleId =user.getRoleId();
		if(roleId == null ){
			throw new BusinessException(-2010, "角色不能为空");
		}
	}
	
	public void checkNoEmptyFields(SysUserVO user,String password) {
		this.checkNoEmptyFields(user);
		if(StringUtils.isEmpty(password)) {
			throw new BusinessException(-2007, "初始密码不能为空");
		}
	}
	
	/**
	 * 
	 * @param userName 用户名 查询条件
	 * @param pageNum	当前页码
	 * @param pageSize	页面大小
	 * @return    
	 * @Description:  用户列表分页查询
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月16日 下午3:42:42   
	 * @version V1.0 
	 */
	public Result<PageInfo<SysUserVO>> queryPage(SysUserVO sysUserVO ,int pageNum, int pageSize,int userId) {
		Result<PageInfo<SysUserVO> > result = new Result<>();
		try {
			SysUserVO user=sysUserManager.getUserInfo(userId);
            if(!"admin".equals(user.getUserName())){
                Integer roleId = sysUserRoleMapper.queryUserRoleId(userId);

                SysRoleOrg org = new SysRoleOrg();
                org.setRoleId(roleId);
                SysRoleOrg roleOrg = sysRoleOrgMapper.querySysRoleOrg(org);
                Integer orgId = roleOrg != null ? roleOrg.getOrgId() : null;

                List<Integer> childOrgIds =new ArrayList<Integer>();
                List<SysOrg> orgList= sysOrgMapper.queryList(null);
                childOrgIds = treeOrgsList(orgList, orgId,childOrgIds);
                //childOrgIds.add(sysOrg.getId());
                sysUserVO.setOrgChildIds(childOrgIds);
            }
            PageInfo<SysUserVO> pageInfo=null;
			pageInfo = sysUserManager.queryPage(sysUserVO ,pageNum, pageSize) ;
			result.put(pageInfo);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	/**
	 * 
	 * @Title:  SysUserService.java   
	 * @param userId 用户id
	 * @return    
	 * @Description: 获取用户详细信息
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月16日 下午3:43:57   
	 * @version V1.0 
	 */
	@SuppressWarnings("unchecked")
	public Result getUserInfo(Integer userId) {
		Result result = new Result();
		try {
			if(userId == null) {
				throw new BusinessException(-3009, "用户id不能为空！");
			}
			SysUserVO user = sysUserManager.getUserInfo(userId);
			user.setRoleId(sysUserRoleMapper.queryUserRoleId(userId));
			result.put(user);
			result.setSuccess();
		}catch(BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	/**
	 * 
	 * @Title:  SysUserService.java   
	 * @param user
	 * @return    
	 * @Description:修改用户信息
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月16日 下午3:46:10   
	 * @version V1.0 
	 */
	public Result updateUserInfo(SysUserVO userVo) {
		Result result = new Result();
		try {
			this.checkNoEmptyFields(userVo);
			
			SysUserVO user = sysUserMapper.selectUserDeailts(userVo.getId());
			
			Map<String,Object>map =new HashMap<String,Object>();
			map.put("mobile", userVo.getMobile());
			
			List<SysUser> list=sysUserMapper.findByMap(map);
			if(!user.getMobile().equals(userVo.getMobile()) && !CollectionUtils.isEmpty(list)){
				result.setError(-20004, "手机号已被占用");
				return result;
			}

			Integer oldOrgId = user.getOrgId();
			Integer nowOrgId = userVo.getOrgId();
			if(oldOrgId != null && !oldOrgId.equals(nowOrgId)) {
				//解除用户与部门角色权限关系
				userVo.setResetRole(false);
				String cacheKey = CacheKeys.KEY_USER_LOGIN_INFO.getKeyPrefix(user.getId());
				redisUtils.del(cacheKey);
			}
			// 新密码
			if(!StringUtils.isEmpty(userVo.getUserPwd())) {
				String newPassword = PasswordUtils.getPassword(userVo.getUserPwd(), user.getSalt()) ;  
				userVo.setUserPwd(newPassword);
			}
			sysUserManager.updateUserInfo(userVo);
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
	 * 
	 * @Title:  SysUserService.java   
	 * @param userIdStr
	 * @param updateId
	 * @return    
	 * @Description: 删除用户信息
	 * @author: Guangjie.Liao     
	 * @date:   2018年3月16日 下午3:46:30   
	 * @version V1.0 
	 */
	@SuppressWarnings("unchecked")
	public Result deleteUser(String userIdStr, Integer updateId) {
		Result result = new Result();
		List<String> userIdList = JsonUtils.json2List(userIdStr);
		try {
			if(CollectionUtils.isEmpty(userIdList)) {
				throw new BusinessException(-2009, "用户id为空，删除失败");
			}
			sysUserManager.deleteUser(userIdList,updateId);
			result.setSuccess();
		}catch(BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
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

    public List<Integer> getStaffIdList(Integer staffId){
        if (staffId == null){
            return  null;
        }
        Integer roleId = sysUserRoleMapper.queryUserRoleId(staffId);

        SysRoleOrg org = new SysRoleOrg();
        org.setRoleId(roleId);
        SysRoleOrg roleOrg = sysRoleOrgMapper.querySysRoleOrg(org);
        Integer orgId = roleOrg != null ? roleOrg.getOrgId() : null;

        List<Integer> childOrgIds =new ArrayList<Integer>();
        List<SysOrg> orgList= sysOrgMapper.queryList(null);
        childOrgIds = treeOrgsList(orgList, orgId,childOrgIds);
        List<Integer> staffIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(childOrgIds)){
            staffIdList = sysUserManager.getStaffIdList(childOrgIds);
        }
        staffIdList.add(staffId);
        return staffIdList;
    }

    
	public static void main(String[] args) {
		/*String salt = RandomStringUtils.randomAlphanumeric(20);
		System.out.println(ShiroUtils.sha256("123456",salt)+","+salt);*/
    }
}
