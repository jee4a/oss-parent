package com.jee4a.oss.auth.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jee4a.oss.auth.common.vo.SysPositionVO;
import com.jee4a.oss.auth.manager.SysPositionManager;
import com.jee4a.oss.auth.mapper.SysPositionMapper;
import com.jee4a.oss.auth.mapper.SysUserMapper;
import com.jee4a.oss.auth.model.sys.SysPosition;
import com.jee4a.oss.auth.model.sys.SysUser;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;

@Service("sysPositionService")
public class SysPositionService extends BaseService {

	@Resource
	private SysPositionManager sysPositionManager;

	@Resource
	private SysUserMapper sysUserMapper;

	@Resource
	private SysPositionMapper sysPositionMapper;

	@SuppressWarnings("rawtypes")
	public Result addPosition(SysPosition position) {
		Result result = new Result();
		try {
			if (StringUtil.isEmpty(position.getPositionName())) {
				throw new BusinessException(-2005, "职位名称不能为空");
			} else {

				// 查询职位名是否已存在
				SysPosition sysPosition = sysPositionMapper
						.selectByPositionName(position.getPositionName());
				if (sysPosition != null) {
					throw new BusinessException(-1000, "职位名称已存在");
				}

			}
			sysPositionManager.addPosition(position);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result queryPage(SysPositionVO position, int pageNum, int pageSize) {
		Result result = new Result();
		try {
			PageInfo<SysPositionVO> pageInfo = sysPositionManager.queryPage(
					position, pageNum, pageSize);
			result.put(pageInfo);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result updatePosition(SysPosition position) {
		Result result = new Result();
		try {
			SysUser user = new SysUser();
			user.setPositionId(position.getId());
			List<SysUser> resultUser = sysUserMapper
					.selectByPrimaryKeySelective(user);
			if (resultUser != null && resultUser.size() > 0) {
				throw new BusinessException(-6005, "该职位下有启用账户不允许禁用");
			}

			// 查询职位名是否已存在
			SysPosition sysPosition = sysPositionMapper
					.selectByPositionName(position.getPositionName());
			if (sysPosition != null && sysPosition.getId() != position.getId()) {
				throw new BusinessException(-1000, "职位名称已存在");
			}

			sysPositionManager.updatePosition(position);
			result.setSuccess();
		} catch (BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Result queryList(SysPosition position) {
		Result result = new Result();
		try {
			List<SysPosition> positionList = sysPositionManager
					.queryList(position);
			result.put(positionList);
			result.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}

	public Result getPositionById(Integer id) {
		Result result = new Result();
		try {
			if (id == null) {
				throw new BusinessException(-7008, "查询id不能为空");
			}
			SysPosition sysPosition = sysPositionMapper.selectByPrimaryKey(id);
			if (sysPosition != null) {
				result.put(sysPosition);
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
}
