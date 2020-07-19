package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class ServiceItem extends LifecycleModel {

	private static final long serialVersionUID = 6097312574701726399L;

	private String name;
	private String code;
	private String memo;
	private String departmentName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
