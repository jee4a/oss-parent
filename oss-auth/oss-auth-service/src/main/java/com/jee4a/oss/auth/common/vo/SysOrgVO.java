package com.jee4a.oss.auth.common.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jee4a.oss.auth.model.sys.SysOrg;

public class SysOrgVO extends SysOrg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * sub资源
	 */
	private List<SysOrgVO> list ;
	
	/**
	 * 父资源名称
	 */
	private String parentName   ;
	
	/**
	 * 创建者
	 */
	private String createName;
	
	
	public static SysOrgVO toVO(SysOrg model) {
		SysOrgVO v = new SysOrgVO() ;
		BeanUtils.copyProperties(model, v);
		return v ;
	}

	public static SysOrg toModel(SysOrgVO vo) {
		SysOrg  model = new SysOrg() ;
		BeanUtils.copyProperties(vo, model);
		return  model  ;
	}
	
	public List<SysOrgVO> getList() {
		return list;
	}


	public void setList(List<SysOrgVO> list) {
		this.list = list;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

}
