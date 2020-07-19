package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class CountryDo extends LifecycleModel {
	private static final long serialVersionUID = 1L;
	private String deparentId;
	private String deptname;

	public String getDeparentId() {
		return deparentId;
	}

	public void setDeparentId(String deparentId) {
		this.deparentId = deparentId;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

}
