package com.jee4a.oss.auth.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jee4a.oss.auth.common.config.BaseConstant;
import com.jee4a.oss.auth.mapper.SysResourceMapper;
import com.jee4a.oss.auth.model.CacheKeys;
import com.jee4a.oss.auth.model.sys.SysResource;
import com.jee4a.oss.framework.BaseManager;
import com.jee4a.oss.framework.QueryHandle;
import com.jee4a.oss.framework.UpdateHandle;

/**
 * @description
 * @date 2018年3月12日
 */
@Component
public class SysResourceManager extends BaseManager {

	@Resource
	private SysResourceMapper sysResourceMapper;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void insertSelective(SysResource record) {
		sysResourceMapper.insertSelective(record);
	}

	/**
	 * 根据主键更新属性不为空的记录
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, value = BaseConstant.DB_OSS)
	public void updateByPrimaryKeySelective(SysResource record) {
		String cacheKey = CacheKeys.KEY_SYS_RESOURCE_ID.getKeyPrefix(record.getId());
		new UpdateHandle() {

			@Override
			public void daoUpdate() {
				sysResourceMapper.updateByPrimaryKeySelective(record);
			}

			@Override
			public void cacheUpdate() {
				redisUtils.del(cacheKey);
			}
		}.execute();

	}

	public SysResource selectByPrimaryKey(Integer id) {
		String cacheKey = CacheKeys.KEY_SYS_RESOURCE_ID.getKeyPrefix(id);
		return new QueryHandle<SysResource>() {

			@Override
			public SysResource cacheQuery() {
				return redisUtils.get(cacheKey, SysResource.class);
			}

			@Override
			public void cacheSet() {
				redisUtils.setex(cacheKey, CacheKeys.KEY_SYS_RESOURCE_ID.getExpire(),
						sysResourceMapper.selectByPrimaryKey(id));
			}

			@Override
			public SysResource daoQuery() {
				return sysResourceMapper.selectByPrimaryKey(id);
			}
		}.execute();
	}

	public List<SysResource> selectNotButtonList(SysResource record) {
		return sysResourceMapper.selectNotButtonList(record);
	}

	public List<SysResource> selectListByParentId(SysResource sysResource) {
		return sysResourceMapper.selectListByParentId(sysResource);
	}

	public List<SysResource> selectList(SysResource record) {
		return sysResourceMapper.selectList(record);
	}

}
