package com.jee4a.oss.auth.common.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jee4a.oss.auth.model.sys.SysResource;

public class SysResourceVO extends  SysResource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * sub资源
	 */
	private List<SysResourceVO> list ;
	
	/**
	 * 父资源名称
	 */
	private String parentName   ;
	
	/**
	 * 是否展开
	 */
	private Boolean open;
	
	
    /** 别名**/
    private String name;
	private Byte show ;
	
	
	public static SysResourceVO toVO(SysResource model) {
		SysResourceVO v = new SysResourceVO() ;
		BeanUtils.copyProperties(model, v);
		return v ;
	}

	public static SysResource toModel(SysResourceVO vo) {
		SysResource  model = new SysResource() ;
		BeanUtils.copyProperties(vo, model);
		return  model  ;
	}
	
	public List<SysResourceVO> getList() {
		return list;
	}


	public void setList(List<SysResourceVO> list) {
		this.list = list;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Byte getShow() {
		return show;
	}

	public void setShow(Byte show) {
		this.show = show;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
