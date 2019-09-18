package com.jee4a.oss.auth.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jee4a.oss.auth.common.enums.SysDeletedEnum;
import com.jee4a.oss.auth.manager.SysDictManager;
import com.jee4a.oss.auth.model.sys.SysDict;
import com.jee4a.oss.framework.BaseService;
import com.jee4a.oss.framework.Result;
import com.jee4a.oss.framework.exceptions.BusinessException;
import com.jee4a.oss.framework.lang.JsonUtils;
@Service("sysDictService")
@SuppressWarnings("rawtypes")
public class SysDictService extends BaseService{
	@Resource
	private SysDictManager sysDictManager;
	
	public Result addDict(SysDict dict) {
		Result result = new Result();
		try {
			this.checkParam(dict);
			sysDictManager.addDict(dict);
			result.setSuccess();
		}catch(BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	public Result queryPage(SysDict record,int pageNum,int pageSize) {
		Result result = new Result();
		try {
			PageInfo<SysDict>  pageInfo =sysDictManager.queryPage(record, pageNum, pageSize);
			result.put(pageInfo);
			result.setSuccess();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	public Result updateDict(SysDict record) {
		Result result = new Result();
		try {
			if(record.getId() == null) {
				throw new BusinessException(-2008, "修改id不能为空");
			}
			sysDictManager.updateDict(record);
			result.setSuccess();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	public Result deleteDict(String ids,Integer userId) {
		Result result = new Result();
		try {
			List<String> dictId = JsonUtils.json2List(ids);
			if(CollectionUtils.isEmpty(dictId)) {
				throw new BusinessException(-2009, "用户id为空，删除失败");
			}
			for (String id : dictId) {
				SysDict dict = new SysDict();
				dict.setId(Integer.valueOf(id));
				dict.setIsDeleted(SysDeletedEnum.DELETED.getK());
				dict.setUpdateTime(new Date());
				dict.setUpdator(userId);
				sysDictManager.updateDict(dict);
			}
			result.setSuccess();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	public Result getDictInfo(Integer id) {
		Result result = new Result();
		try {
			if(id == null) {
				throw new BusinessException(-2009, "查询参数为空");
			}
			result.setResult(sysDictManager.getDictInfo(id));
			result.setSuccess();
		}catch(BusinessException e) {
			result.setError(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setDefaultError();
		}
		return result;
	}
	
	public void checkParam(SysDict dict) throws Exception{
		if(StringUtil.isEmpty(dict.getDicName())) {
			throw new BusinessException(-2005, "字典名称不能为空");
		}
		if(StringUtil.isEmpty(dict.getDicType())) {
			throw new BusinessException(-2006, "字典类型不能为空");
		}
		if(StringUtil.isEmpty(dict.getDicCode())) {
			throw new BusinessException(-2007, "字典码不能为空");
		}
		if(StringUtil.isEmpty(dict.getDicValue())) {
			throw new BusinessException(-2008, "字典值不能为空");
		}
		//查询字典
		SysDict dic = sysDictManager.selectByTypeAndCode(dict.getDicType(),dict.getDicCode());
		if(dic != null) {
			throw new BusinessException(-3008, "插入字典类型和字典码重复，请重新输入。");
		}
	}
	
}
