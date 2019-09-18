package com.jee4a.oss.auth.common.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jee4a.oss.auth.model.sys.SysPosition;

public class SysPositionVO extends SysPosition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SysPositionVO> list ;

	private String createName;
	
	public static SysPositionVO toVO(SysPosition model) {
		SysPositionVO v = new SysPositionVO() ;
		BeanUtils.copyProperties(model, v);
		return v ;
	}

	public static SysPosition toModel(SysPositionVO vo) {
		SysPosition  model = new SysPosition() ;
		BeanUtils.copyProperties(vo, model);
		return  model  ;
	}
	
	public List<SysPositionVO> getList() {
		return list;
	}


	public void setList(List<SysPositionVO> list) {
		this.list = list;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
