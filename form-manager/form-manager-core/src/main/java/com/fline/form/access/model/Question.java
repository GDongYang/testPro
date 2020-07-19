package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;

/**
 * 
 * @author wangn
 * 2019-04-28
 * c_question表实体类  
 */


public class Question extends LifecycleModel {

	
	
	private static final long serialVersionUID = -3099337261573181087L;
	@Column(name = "身份证号", column = "cerno")
	private String cerno;
	@Column(name = "部门id", column = "departmentId")
	private Long departmentId;
	@Column(name = "模板id", column = "certTempId")
	private Long certTempId;
	@Column(name = "部门名称")
	private String department;
	@Column(name = "模板名称")
	private String certTemp;
	@Column(name = "问题描述", column = "bewrite")
	private String bewrite;
	
	
	
	

	public String getBewrite() {
		return bewrite;
	}

	public void setBewrite(String bewrite) {
		this.bewrite = bewrite;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
	}

	public Long getCertTempId() {
		return certTempId;
	}

	public void setCertTempId(Long certTempId) {
		this.certTempId = certTempId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCertTemp() {
		return certTemp;
	}

	public void setCertTemp(String certTemp) {
		this.certTemp = certTemp;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
