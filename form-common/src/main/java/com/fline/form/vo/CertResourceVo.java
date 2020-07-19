package com.fline.form.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhaoxz
 * 2019-5-7
 * r_cert_resource表实体类
 *
 */
public class CertResourceVo implements Serializable  {

	private static final long serialVersionUID = 6543503189324616919L;
	//@Column(name = "模板表id", column = "tempId")
	private int tempId;
	//@Column(name = "模板表编码", column = "tempCode")
	private String tempCode;
//	@Column(name = "资源编码", column = "resourceCode")
	private String resourceCode;

	private List<CertResourceFieldVo> fieldList;
	
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getTempCode() {
		return tempCode;
	}
	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}
	public List<CertResourceFieldVo> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<CertResourceFieldVo> fieldList) {
		this.fieldList = fieldList;
	}
	
}
